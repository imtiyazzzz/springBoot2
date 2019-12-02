package com.example.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.example.domain.PasswordResetOtp;
import com.example.domain.User;
import com.example.dto.AuthDTO;
import com.example.dto.PasswordResetOtpDTO;
import com.example.dto.UserDTO;
import com.example.repository.UserRepository;
import com.example.service.ConfigService;
import com.example.service.PasswordResetOtpService;
import com.example.service.UserService;
import com.example.util.Constants;
import com.example.util.Messages;
import com.example.util.transformer.UserMapper;
import com.sixsprints.auth.dto.AuthResponseDTO;
import com.sixsprints.auth.dto.Authenticable;
import com.sixsprints.auth.service.Impl.AbstractAuthService;
import com.sixsprints.core.dto.MetaData;
import com.sixsprints.core.exception.EntityAlreadyExistsException;
import com.sixsprints.core.exception.EntityInvalidException;
import com.sixsprints.core.exception.EntityNotFoundException;
import com.sixsprints.core.exception.NotAuthenticatedException;
import com.sixsprints.core.exception.NotAuthorizedException;
import com.sixsprints.core.utils.EncryptionUtil;
import com.sixsprints.core.utils.RandomUtil;
import com.sixsprints.notification.dto.EmailDto;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl extends AbstractAuthService<User> implements UserService {

	@Resource
	private UserRepository userRepository;

	@Resource
	private UserMapper userMapper;
	@Resource
	private PasswordResetOtpService passwordResetOtpService;
	@Resource
	private ConfigService config;

//  @Override
//  protected GenericRepository<User> repository() {
//    return userRepository;
//  }

	@Override
	public AuthResponseDTO<UserDTO> signup(UserDTO userDTO)
			throws EntityAlreadyExistsException, EntityInvalidException {
		User user = userMapper.toEntity(userDTO);
		AuthResponseDTO<User> registered = super.register(user);
		return convertToAuthRespDTO(registered);
	}

	@Override
	public AuthResponseDTO<UserDTO> signin(AuthDTO authDTO) throws NotAuthenticatedException, EntityNotFoundException {
		AuthResponseDTO<User> logged = super.login(authDTO);
		return convertToAuthRespDTO(logged);
	}

//  @Override
//  public AuthResponseDTO<UserDTO> adminSignin(AuthDTO authDTO)
//    throws NotAuthenticatedException, EntityNotFoundException, NotAuthorizedException {
//    AuthResponseDTO<User> logged = super.login(authDTO);
//    User user = logged.getData();
//    Role role = roleService.findByName(user.getRoleName());
//    Boolean hasAccess = PermissionUtil.hasAccess(role, EntityPermission.ADMIN_PANEL, AccessPermission.ANY);
//    if (!hasAccess) {
//      throw NotAuthorizedException.childBuilder().build();
//    }
//    return convertToAuthRespDTO(logged);
//  }

	@Override
	public UserDTO tokenValidation(User user) {
		return userMapper.toDto(user);
	}
//
//  @Override
//  public void signout(User user, String token) {
//    List<String> invalidTokens = user.getInvalidTokens();
//    if (CollectionUtils.isEmpty(invalidTokens)) {
//      user.setInvalidTokens(new ArrayList<>());
//      invalidTokens = user.getInvalidTokens();
//    }
//    // to keep the invalid token size upto 5
//    if (invalidTokens.size() > 5) {
//      invalidTokens.remove(0);
//    }
//    invalidTokens.add(token);
//    save(user);
//  }

	@Override
	public void updateAllFromDto(List<UserDTO> userDTOs) {
		super.updateAll(userMapper.toDomain(userDTOs));
	}

	private AuthResponseDTO<UserDTO> convertToAuthRespDTO(AuthResponseDTO<User> registered) {
		UserDTO userDTO = userMapper.toDto(registered.getData());
		return AuthResponseDTO.<UserDTO>builder().token(registered.getToken()).data(userDTO).build();
	}

	@Override
	protected boolean isInvalid(User domain) {
		return domain == null || StringUtils.isBlank(domain.getEmail());
	}

	@Override
	protected User findDuplicate(User entity) {
		return userRepository.findByEmailOrMobileNumber(entity.getEmail(), entity.getMobileNumber());
	}

	// required checks and operations, before creating new User
	@Override
	protected void preCreate(User user) {

		// if password blank then set default password (email itself) else encrypt it
		if (StringUtils.isBlank(user.getPassword())) {
			user.setPassword(EncryptionUtil.encrypt(user.getEmail()));
		} else
			user.setPassword(EncryptionUtil.encrypt(user.getPassword()));

		// if role blank then set default role (USER)
		if (StringUtils.isBlank(user.getRoleName())) {
			user.setRoleName(Constants.DEFAULT_USER_ROLE);
		}

		// if mobile number is not as per the standard then make it "(+91)9876543210"
//    String mobileNumber = user.getMobileNumber();
//    if (!mobileNumber.matches(Constants.DEFAULT_MOBILE_NUMBER_REGEX)) {
//      Country country = countryService.findByCountryName(user.getCountryName());
//      user.setMobileNumber("(" + country.getCallingCode() + ")" + user.getMobileNumber());
//    }
//
//    // if wallet blank then create wallet and set wallet slug in current user
//    if (StringUtils.isBlank(user.getWalletSlug())) {
//      try {
//        user.setWalletSlug(
//          walletService.create(Wallet.builder()
//            .balance(BigDecimal.ZERO)
//            .userName(user.getFirstName() + " " + user.getLastName())
//            .expiryDate(Wallet.defaultExpiryDate)
//            .build()).getSlug());
//      } catch (EntityAlreadyExistsException | EntityInvalidException e) {
//        user.setWalletSlug("");
//      }
//    }

		// if booklet blank then create booklet and set booklet slug in current user
//    if (StringUtils.isBlank(user.getBookletSlug())) {
//      try {
//        user.setBookletSlug(
//          bookletService.create(Booklet.builder().vouchers(new ArrayList<Voucher>()).build()).getSlug());
//      } catch (EntityAlreadyExistsException | EntityInvalidException e) {
//        user.setBookletSlug("");
//      }
//    }

		// if cart null then create and set cart in current user
//    if (user.getCart() == null) {
//      user.setCart(Cart.builder().vouchers(new ArrayList<Voucher>()).build());
//    }

	}

	@Override
	protected User findByAuthCriteria(String authId) {
		return userRepository.findByEmail(authId);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	protected boolean isPasscodeSame(User domain, String passcode) {
		return domain.getPassword().equals(EncryptionUtil.encrypt(passcode));
	}

	@Override
	protected void genSaveMailOTP(User user) {
		// generate otp
		String otp = String.valueOf(RandomUtil.randomInt(1000, 9999));

		// save otp
		PasswordResetOtp passwordResetOtp = PasswordResetOtp.builder().otp(otp).email(user.getEmail()).build();
		try {
			passwordResetOtpService.saveOrUpdate(passwordResetOtp);
		} catch (EntityInvalidException e) {
			log.error(e.getMessage());
		}

		// mail otp
		log.info("Your OTP: " + otp);
		sendEmail(user, otp);
	}

	@Override
	protected void updatePassword(Authenticable authenticable) throws EntityInvalidException {
		PasswordResetOtpDTO passwordResetOtpDTO = (PasswordResetOtpDTO) authenticable;
		// take otp, find T
		PasswordResetOtp passwordResetOtp = ((PasswordResetOtpServiceImpl) passwordResetOtpService)
				.findDuplicate(PasswordResetOtp.builder().email(passwordResetOtpDTO.getEmail())
						.otp(passwordResetOtpDTO.getOtp()).build());

		// verify for valid otp
		if (passwordResetOtp == null || !StringUtils.equals(passwordResetOtp.getOtp(), passwordResetOtpDTO.getOtp())) {
			throw EntityInvalidException.childBuilder().error("Invalid OTP").data(passwordResetOtp).build();
		}
		// verify for expired otp
		if (DateTime.now().isAfter(
				new DateTime(passwordResetOtp.getDateCreated()).plusMinutes(Constants.OTP_VALIDITY_IN_MINUTES))) {
			throw EntityInvalidException.childBuilder().error("OTP expired").data(passwordResetOtp).build();
		}

		// if all ok then password update of T
		User user = findByAuthCriteria(passwordResetOtpDTO.getEmail());
		user.setPassword(EncryptionUtil.encrypt(authenticable.passcode()));
		save(user);
		passwordResetOtpService.delete(passwordResetOtp);
	}

	@Override
	protected MetaData<User> metaData(User entity) {
		return MetaData.<User>builder().collection("user").prefix("USR").classType(User.class)
				.dtoClassType(UserDTO.class).fields(config.get().getUserFields()).build();
	}

	@Override
	protected EntityInvalidException invalidException(User domain) {
		return EntityInvalidException.childBuilder().error(Messages.USER_IS_INVALID).data(domain).build();
	}

	@Override
	protected EntityAlreadyExistsException alreadyExistsException(User domain) {
		return EntityAlreadyExistsException.childBuilder().error(Messages.USER_ALREADY_EXISTS)
				.arguments(new String[] { domain.getEmail(), domain.getMobileNumber() }).build();
	}

	@Override
	protected NotAuthenticatedException notAuthenticatedException(User domain) {
		return NotAuthenticatedException.childBuilder().error(Messages.LOGIN_FAILED_MISMATCH).data(domain).build();
	}

	@Override
	protected EntityNotFoundException notRegisteredException(String email) {
		return EntityNotFoundException.childBuilder().error(Messages.LOGIN_FAILED_UNREGISTERED).data(email).build();
	}

	@Override
	public AuthResponseDTO<UserDTO> adminSignin(AuthDTO authDTO)
			throws NotAuthenticatedException, EntityNotFoundException, NotAuthorizedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void signout(User domain, String token) {
		// TODO Auto-generated method stub

	}

//@Override
//protected void updatePassword(Authenticable authenticable) throws EntityInvalidException {
//	// TODO Auto-generated method stub
//	
//}
	private void sendEmail(User user, String otp) {
		EmailDto email = EmailDto.builder().to(user.getEmail()).subject("OTP for TxR")
				.content(String.format("Your OTP: %s", otp)).build();
		System.out.println(email);
	}

}

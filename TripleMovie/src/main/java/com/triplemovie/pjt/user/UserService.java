package com.triplemovie.pjt.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.triplemovie.pjt.SecurityUtils;
import com.triplemovie.pjt.user.model.UserTicketVO;
import com.triplemovie.pjt.user.model.UserVO;


@Service
public class UserService {
	
	@Autowired
	private UserMapper mapper;
	
	public int login(UserVO param) {
		if(param.getUser_id().equals("")) { return 2; }
		
		UserVO dbUser = mapper.selUser(param);		
		if(dbUser == null) { return 2; }
		
		String cryptPw = SecurityUtils.getEncrypt(param.getUser_pw(), dbUser.getSalt());
		if(!cryptPw.equals(dbUser.getUser_pw())) { return 3; }
		
		param.setI_user(dbUser.getI_user());
		param.setUser_pw(null);
		param.setUser_id(dbUser.getUser_id());
		param.setNick_nm(dbUser.getNick_nm());
		param.setAge(dbUser.getAge());
		param.setGender(dbUser.getGender());
		return 1;		
	}
	
	public int join(UserVO param) {
		String pw = param.getUser_pw();
		String salt = SecurityUtils.generateSalt();
		String cryptPw = SecurityUtils.getEncrypt(pw, salt);
		
		param.setSalt(salt);
		param.setUser_pw(cryptPw);
		
		return mapper.insUser(param);
	}
	
	public int chkNick(UserVO param) {
		UserVO dbUser = mapper.selUser(param);
		if(dbUser == null) { return 2; }
		return 3;
	}
	
	public List<UserTicketVO> selTicketInfo(int i_user) {
		List<UserTicketVO> param = mapper.selTicketInfo(i_user);
		for(UserTicketVO vo : param) {
			List<String> seat = mapper.selSeatsInfo(vo.getI_ticket());
			String[] seats = new String[seat.size()];
			
			for(int i=0; i<seat.size(); i++) {
				seats[i] = seat.get(i);
			}		
			String s_dt = String.format("%02d", (vo.getS_dt()/60)) + " : " + String.format("%02d", (vo.getS_dt()%60));
			vo.setS_Time(s_dt);
			vo.setSeats(seats);
		}
		return param;
	}

	public int delTicket(UserTicketVO param) {
		mapper.delSeats(param);
		return mapper.delTicket(param);
	}
}

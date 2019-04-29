package cn.tedu.store.controller;

import cn.tedu.store.controller.ex.FileEmptyException;
import cn.tedu.store.controller.ex.FileSizeException;
import cn.tedu.store.controller.ex.FileUploadException;
import cn.tedu.store.controller.ex.*;
import cn.tedu.store.service.ex.*;
import cn.tedu.store.util.ResponseResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 控制器类的基类
 */
public class BaseController {
	
	/**
	 * 处理请求时，用于表示操作正确的代码
	 */
	public static final int SUCCESS = 200;
	
	/**
	 * 从Session中获取当前登录的用户的id
     * @param session
	 * @return 当前登录的用户的id
	 */
	protected Integer getUidFromSession(HttpSession session) {
		return Integer.valueOf(session.getAttribute("uid").toString());
	}

    @ExceptionHandler({ServiceException.class,
            FileUploadException.class})
	public ResponseResult<Void>
    handleException(Exception e) {
		// 声明返回对象
		ResponseResult<Void> rr = new ResponseResult<Void>();
		// 在返回对象封装错误提示的文字
		rr.setMessage(e.getMessage());

        // 根据异常的不同，返回的错误代码也不同
		if (e instanceof UserConflictException) {
			// 400-用户名冲突
			rr.setState(400);
		} else if (e instanceof UserNotFoundException) {
			// 401-用户数据不存在的异常
			rr.setState(401);
		} else if (e instanceof PasswordNotMatchException) {
			// 402-验证用户密码失败的异常
			rr.setState(402);
		} else if (e instanceof InsertException) {
			// 500-插入数据异常
			rr.setState(500);
		} else if (e instanceof UpdateException) {
			// 501-更新数据异常
			rr.setState(501);
		} else if (e instanceof FileEmptyException) {
			// 601-上传的文件为空的异常
			rr.setState(601);
		} else if (e instanceof FileContentTypeException) {
			// 602-上传的文件类型错误的异常
			rr.setState(602);
		} else if (e instanceof FileSizeException) {
			// 603-上传的文件大小超出限制的异常
			rr.setState(603);
		} else if (e instanceof FileUploadIOException) {
			// 604-上传文件时读写异常
			rr.setState(604);
		}

        // 返回
		return rr;
	}

}








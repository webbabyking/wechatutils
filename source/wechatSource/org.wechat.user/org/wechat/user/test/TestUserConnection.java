package org.wechat.user.test;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.wechat.common.entity.results.JsonResult;
import org.wechat.common.entity.results.WechatResult;
import org.wechat.user.conn.UserConnection;
import org.wechat.user.entity.UserOpenListInfo;
import org.wechat.user.entity.WeixinUser;
import org.wechat.user.request.BatchGet;
import org.wechat.user.request.BatchGetRequest;
import org.wechat.user.utils.UserConvertUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 测试用户连接
 * @author Andy
 *
 */
public class TestUserConnection {
	private UserConnection userConn;
	private String accessToken = "";
	
	@Before
	public void init(){
		userConn = new UserConnection();
		accessToken = "-y7ZrsMlEp5Z8EaQvUI_f6OxIv6xFG5YNp-QU40_8XLxDGqLjj7Xua5iKO3KeIuvOBKE_GVC6xLvPvjORjFvOPK47w06KRpDDSDLDBaU9Oo";
	}
	/**
	 * 第一种方式
	 * {"group":{"name":"test"}}
	 */
	@Test
	public void testJsonData(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("name","test");
		Map<String,Map<String,String>> testMap = new HashMap<String,Map<String,String>>();
		testMap.put("group",map);
		System.out.println(JSONObject.toJSONString(testMap));
	}
	
	@Test
	public void testClassName(){
		String jsonStr ="{errcode:0,errmsg:ok}";
		
	}
	
	/**
	 * 排序
	 */
	@Test
	public void testSort(){
		String[] array = {"aewewewe","sdsdsdsd","cdewewew"};
		Arrays.sort(array);
		for(String str:array){
			System.out.println(str);
		}
	}
	
	/**
	 * 测试创建分组
	 * {"group":{"id":101,"name":"testName"}}
	 */
	@Test
	public void testCreateGroup(){
		String result = userConn.createGroup(accessToken, "testName");
		Assert.assertNotNull(result);
		System.out.println(result);
	}
	
	/**
	 * 获取分组信息
	 * {"groups":[{"id":0,"name":"默认组","count":157},{"id":1,"name":"屏蔽组","count":0},{"id":2,"name":"星标组","count":0},{"id":100,"name":"testName","count":0}]}
	 */
	@Test
	public void testQueryGroups(){
		String result=userConn.queryGroups(accessToken);
		Assert.assertNotNull(result);
		System.out.println(result);
	}
	
	/**
	 * 测试指定查询用户所在的用户
	 * 返回结果 	 {"groupid":0}
	 */
	@Test
	public void testGetGroupId(){
		String openid = "obc-3jttvh09pNP8BmodxhkxaCzo";
		String result=userConn.getGroupId(accessToken,openid);
		Assert.assertNotNull(result);
		System.out.println(result);
	}
	
	/**
	 * 修改分组名
	 * JsonResult
	 * errorCode-->0  getErrmsg-->ok 表示成功
	 */
	@Test
	public void testUpdateGroupName(){
		    JsonResult result = userConn.updateGroupName(accessToken,101, "新的分组名称");
			Assert.assertNotNull(result);
			System.out.println("errorCode-->"+result.getErrcode());
			System.out.println("getErrmsg-->"+result.getErrmsg());
		}
	
	/**
	 * 移动用户分组
	 * JsonResult
	 * errorCode-->0  getErrmsg-->ok 表示成功
	 */
	@Test
	public void testMoveUserGroup(){
		String openid = "oZq7Bt2gISuKEQQeSoEogSNj2AzQ";
		JsonResult result = userConn.moveUserGroup(accessToken, openid,100);
		Assert.assertNotNull(result);
		System.out.println("errorCode-->"+result.getErrcode());
		System.out.println("getErrmsg-->"+result.getErrmsg());
	}
	
	/**
	 * 设置用户的备注名称
	 * JsonResult
	 * errorCode-->0  getErrmsg-->ok 表示成功
	 */
	@Test
	public void testRemarkUserName(){
		String openid = "obc-3jttvh09pNP8BmodxhkxaCzo";
		JsonResult result = userConn.remarkUserName(accessToken,openid,"测试备注名称");
		Assert.assertNotNull(result);
		System.out.println("errorCode-->"+result.getErrcode());
		System.out.println("getErrmsg-->"+result.getErrmsg());
	}
	
	/**
	 * 获取用户基本信息
	 * {"subscribe":1,"openid":"obc-3jttvh09pNP8BmodxhkxaCzo","nickname":"誓言","sex":1,
	 */
	@Test
	public void testUnionIDUserInfo(){
		String openid = "o8ed_jv3vIC6l7Y8WQybls0xl8n0";
		String accessToken="VGSVaykTyyS6N2KHlrQOPOYIwQ62OXGHtQMBLRx7y9nFNZgLwm8J-OR8ZT97WMtqk_-C4YaSi5KXmuGlx0t4edY7GJRgnUIGAxzYNp_Q5Dg";
		WechatResult obj = userConn.unionIDUserInfo(accessToken,openid,"zh_CN");
		WeixinUser user  = (WeixinUser) obj.getObj();
		assertNotNull(user);
		System.out.println(user.getOpenid());
	}
	
	/**
	 * 
	 * 测试获取用户列表
	 * "total":157,"count":157,"data":{"openid":["obc-3jnpwZvfJRqRT0gfgeMe7EVc","obc-3jq8Gw_ax3Tbt43tisYRKbBs","obc-3jhk5mEhKrJp2JOsaG0cgYLg","obc-3jtssch1YUHqgvz04sFEJ8lM","
	 */
	@Test
	public void testGetUserList(){
		String result = userConn.getUserOpenList(accessToken, "");
		WechatResult result1 = UserConvertUtils.getOpenIdList(result);
		List<WeixinUser> totalList = new ArrayList<WeixinUser>();
		if(result1.isSuccess()){
			UserOpenListInfo info = (UserOpenListInfo)result1.getObj();
			int total = info.getTotal();
			System.out.println(total);
			int size = (total%100==0)?total/100:(total/100)+1;									//该处设置为100 是因为文档上每次只能获取100条用户的详细信息
			for(int i =0;i<size;i++){
				int fromIndex = i*100;
				int toIndex = (i==size-1)?(fromIndex+(total-i*100)):(i+1)*100;
				List<String> newList = info.getOpenid().subList(fromIndex,toIndex);
				WechatResult result2 = UserConvertUtils.convertRequestParams(newList,"");
				if(result2.isSuccess()){
					BatchGetRequest request2 = (BatchGetRequest) result2.getObj();
					String data = UserConnection.batchgetUserList(accessToken, request2);
					WechatResult result3 = UserConvertUtils.getListUserInfo(data);
					 List<WeixinUser> list = (List<WeixinUser>) result3.getObj();
					 totalList.addAll(list);
				}
			}
		}
		System.out.println(totalList.size());
		assertNotNull(result);
		System.out.println(result);
	}
	/**
	 * 刷新access_token
	 */
	@Test
	public void testGetTokenByRefreshToken(){
		String appid = "";
		String refreshToken = "";
		WechatResult token = userConn.getTokenByRefreshToken(appid, refreshToken);
		if(token.isSuccess()){
			System.out.println(token.isSuccess());
		}
	}
	
	/**
	 * 拉取用户信息(需scope为 snsapi_userinfo)
	 */
	@Test
	public void testGetBySnsapiUserInfo(){
		String openid = "o8ed_jv3vIC6l7Y8WQybls0xl8n0";
		String token="OezXcEiiBSKSxW0eoylIeEDjCaTvqOjan-GmTNbArTy8b2UBfJFrjcunAk2wQbUjJHGTepsD3vvGj6Fbkxer_h9PfXm_-5IAzDj6aUsPZN8AUHhbDKHhIloCY0fZzMhwESoav7FqOn5aYih7h4TZZg";
		WechatResult user = userConn.getBySnsapiUserInfo(token,openid,"zh_CN");
		assertNotNull(user);
		WeixinUser wUser = (WeixinUser) user.getObj();
		System.out.println(user.getMsg());
	}

	/**
	 * 测试检验授权凭证（access_token）是否有效
	 * errorCode-->40001
	 * getErrmsg-->invalid credential		无效
	 */
	@Test
	public void testTestTokenValid(){
		String openid = "o8ed_jv3vIC6l7Y8WQybls0xl8n0";
		JsonResult result = userConn.testTokenValid("OezXcEiiBSKSxW0eoylIeEDjCaTvqOjan-GmTNbArTy8b2UBfJFrjcunAk2wQbUjO5tnn-eXSlOEy9ajI_voKP4ODomR22di26oSAEepRnf-eYrwOa-LaF5yijZJScGgQAjHXKXB0jODXcSCq-pFkw",openid);
		Assert.assertNotNull(result);
		System.out.println("errorCode-->"+result.getErrcode());
		System.out.println("getErrmsg-->"+result.getErrmsg());
	}
	
	@Test
	public void testTokenPath(){
		try {
			String path = UserConnection.getAuthCodeUrl("wx7c50b0eb849348d3","http://testmall.duapp.com/redirectAction","snsapi_userinfo","state");
			System.out.println(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 批量获取用户信息
	 */
	@Test
	public void testBatchGet(){
		BatchGetRequest request = new BatchGetRequest();
		List<BatchGet> list = new ArrayList<BatchGet>();
		BatchGet get = new BatchGet();
		get.setOpenid("o8ed_jnF1YRLG9KShkrockKbpQlI");
		list.add(get);
		request.setUser_list(list);
		String data = UserConnection.batchgetUserList(accessToken,request);
		System.out.println(data);
		WechatResult result = UserConvertUtils.getListUserInfo(data);
		if(result.isSuccess()){
			List<WeixinUser> list1 = (List<WeixinUser>) result.getObj();
			System.out.println(list1.size());
		}
	}
}

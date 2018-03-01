package com.champion.crawler.convert;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.SystemException;

import com.champion.crawler.html.parser.Parser;

/**
 * 
 * 1:��JavaBeanת����Map��JSONObject
 * 2:��Mapת����Javabean
 * 3:��JSONObjectת����Map��Javabean
 * 
 * @author Alexia
 */

public class JsonHelper {
    
    /**
     * ��Javabeanת��ΪMap
     * 
     * @param javaBean
     *            javaBean
     * @return Map����
     */
    public static Map toMap(Object javaBean) {

        Map result = new HashMap();
        Method[] methods = javaBean.getClass().getDeclaredMethods();

        for (Method method : methods) {

            try {

                if (method.getName().startsWith("get")) {

                    String field = method.getName();
                    field = field.substring(field.indexOf("get") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);

                    Object value = method.invoke(javaBean, (Object[]) null);
                    result.put(field, null == value ? "" : value.toString());

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return result;

    }

    /**
     * ��Json����ת����Map
     * 
     * @param jsonObject
     *            json����
     * @return Map����
     * @throws JSONException
     */
    public static Map toMap(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
        
        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        
        while (iterator.hasNext()) {

            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key.toUpperCase(), value);
        }
        return result;

    }

    /**
     * ��JavaBeanת����JSONObject��ͨ��Map��ת��
     * 
     * @param bean
     *            javaBean
     * @return json����
     */
    public static JSONObject toJSON(Object bean) {

        return new JSONObject(toMap(bean));

    }

    /**
     * ��Mapת����Javabean
     * 
     * @param javabean
     *            javaBean
     * @param data
     *            Map����
     */
    public static Object toJavaBean(Object javabean, Map data) {

        Method[] methods = javabean.getClass().getDeclaredMethods();
        for (Method method : methods) {

            try {
                if (method.getName().startsWith("set")) {

                    String field = method.getName();
                    field = field.substring(field.indexOf("set") + 3);
                    field = field.toLowerCase().charAt(0) + field.substring(1);   
                    field = field.toUpperCase();
                    method.invoke(javabean, new Object[] {
                    data.get(field)
                    });

                }
            } catch (Exception e) {
            	System.out.println(e.getMessage());
            }
        }

        return javabean;

    }

    /**
     * JSONObject��JavaBean
     * 
     * @param bean
     *            javaBean
     * @return json����
     * @throws java.text.ParseException
     *             json�����쳣
     * @throws JSONException
     */
    public static void toJavaBean(Object javabean, String jsonString)
            throws ParseException, JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
    
        Map map = toMap(jsonObject.toString());
        
        toJavaBean(javabean, map);
    }
    
    
    public static void main(String[] args) throws ParseException, JSONException {
//		String jsonstr="{\"id\":1994955,\"iname\":\"������Ͽ����ũ��Ʒ�����Ƿ�չ���޹�˾\",\"caseCode\":\"(2015)��ִ�ֵ�00242��\",\"cardNum\":\"55959789-9\",\"businessEntity\":\"��ʸ�\",\"courtName\":\"����������Ժ\",\"areaName\":\"����\",\"partyTypeName\":\"581\",\"gistId\":\"(2015)������ֵ�41��\",\"regDate\":\"2015��07��07��\",\"gistUnit\":\"����������������Ժ\",\"duty\":\"һ��ԭ�����Ľ��뱻�渣����Ͽ����ũ��Ʒ�����Ƿ�չ���޹�˾ǩ���ĺ�ϿʳƷ���ǵ�A17����49����Ʒ��������Э������ �������渣����Ͽ����ũ��Ʒ�����Ƿ�չ���޹�˾ȷ����Ƿԭ�����Ľ�������������𡢶��𹲼�150000Ԫ����Ϣ����Ϣ��2015��1��1������ʵ�ʻ������֮��ֹ���й���������ͬ��ͬ��������ʼ��㣩������2015��6��30��ǰ���塣�����������ԭ�����Ľ���ָ���˻�����������ԲȦ֧�У��������Ľ����˺�4340611850038717���������ʱ��˫���򱾰�������Ȩ�������սᡣ �������������3300Ԫ��������ȡ1650Ԫ���ɱ��渣����Ͽ����ũ��Ʒ�����Ƿ�չ���޹�˾�������������յ�������֮��������֮����Ժ���ɡ�\",\"performance\":\"ȫ��δ����\",\"disruptTypeName\":\"Υ���Ʋ������ƶ�,�����������������ܲ�������Ч��������ȷ������\",\"publishDate\":\"2015��07��09��\"}";
		Parser parser = new Parser();
		
//		Map<String,String> parmas = new HashMap<String, String>();
//		parmas.put("ciEnter", "true");
//		parmas.put("org","1586");
//		parmas.put("id", "16111488");
//		parmas.put("seq_id", "18");
//		parmas.put("specificQuery", "specificQuery");
//		String url ="http://www.jsgsj.gov.cn:58888/ecipplatform/ciServlet.json";
////		ciEnter=true&org=1586&id=16111488&seq_id=18&specificQuery=basicInfo
	parser.setDocByUrl("http://api.zdoz.net/transmore.ashx?lats=34.123;34.332;55.231&lngs=113.123;112.213;115.321&type=1");
////		parser.setDocByByUrlPost(url, parmas);
		
  	String jsonstr = parser.doc.text();
		
//     	jsonstr = jsonstr.replace("[", "");
//    	jsonstr = jsonstr.replace("]", "");
    	jsonstr = "{\"items\":"+jsonstr+"}";
//    	jsonstr= jsonstr.toLowerCase();
//		DishonestyLegalPersonDetail personDetail = new DishonestyLegalPersonDetail();
//    	JSONObject jsonObject = new JSONObject(jsonstr);
//    	JSONArray changeObjs =  jsonObject.getJSONArray("items");
//   	for(int i = 0 ;i<changeObjs.length();i++){
//   		JSONObject object = changeObjs.getJSONObject(i);
//   		jsonstr = object.toString();
//   		JSEnterpiseBaseInfo jsEnterpiseBaseInfo = new JSEnterpiseBaseInfo();
//   		toJavaBean(jsEnterpiseBaseInfo, jsonstr);
////   		JSEnterpiseBaseInfoImpl impl = new JSEnterpiseBaseInfoImpl();
//   		impl.addJSEnterpriseBaseInfo(jsEnterpiseBaseInfo);		
//   		System.out.println(jsEnterpiseBaseInfo.getC1());    		
   	}
    	
    	
		
}
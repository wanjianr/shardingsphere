package com.app.miniapp.redis.dto;


import java.util.Arrays;
import java.util.List;

/**
 * <p>PURPOSE: 常量类
 * <p>DESCRIPTION:
 * <p>CALLED BY:
 * <p>CREATE DATE: 2022-12-05
 * <p>UPDATE DATE:
 * <p>UPDATE USER:
 * <p>HISTORY:
 * @version 1.0
 * @author ltx
 * @since java 1.8
 * @see
 */
public class CommonConstant {

	/**
	 * 版本1.0.0
	 */
	public static String VERSION_100 = "1.0.0";

	/**
	 * 签名类型 SM2
	 */
	public static String SIGN_TYPE_SM2 = "SM2";

	/**
	 * 业务编号 1401
	 */
	public static String BIZ_NO_1401 = "1401";

	/**
	 * 结算清单默认值
	 */
	public static String SETL = "setl";

	/**
	 * 常量 000
	 */
	public static final String COMMON_000 = "000";

	/**
	 * 保留四位小数
	 */
	public static final String FORMAT = "0.0000";

	/**
	 * 保留6位小数
	 */
	public static final String FORMAT_SIX = "0.000000";

	/**
	 * 保留二位小数
	 */
	public static final String FORMAT_1 = "0.00";

	/**
	 * 消息重发标志
	 */
	public static final String MSG_TYPE="1";

	/**
	 * 消息重发标志
	 */
	public static final String MSG_RESEND_TYPE="1";

	/**
	 * 消息发送成功标志
	 */
	public static final Integer MSG_IS_SUC=1;

	/**
	 * 中台系统内部错误码 9999
	 */
	public static final String MID_ERR_9999 = "9999";

	/**
	 * 未知错误码999
	 */
	public static final String ERR_999 = "999";
	/**
	 * 未查询到相应的票据信息错误码998
	 */
	public static final Integer ERR_998 = 998;
	/**
	 * 存在未上传成功数据，请上传后再入账997
	 */
	public static final Integer ERR_997 = 997;
	/**
	 * 中台返回 SUCCESS代表成功；FAILED代表失败
	 */
	public static final String FAILED = "FAILED";

	/**
	 * int 18
	 */
	public static final int INT_18  = 18;

	/**
	 * 通知获取单位备用票分配结果（2-4获取单位备用票分配结果）
	 */
	public static final String INVOICE_APP = "002004";

	/**
	 * 通知单位端票据申请被驳回
	 */
	public static final String REJECT_NONTAX_TICKET_INFO = "002009";

	/**
	 * 通知执收单位端更新票号段分配结果信息（2-2.票据执收单位号段分配结果信息查询）
	 */
	public static final String INVOICE_DIS_RESULT = "002002";

	/**
	 * 财政电子票据下载接口
	 */
	public static final String DOWN_BILL = "008001";

	/**
	 * 财政冲红电子票据下载接口
	 */
	public static final String DOWN_RED_BILL = "008003";

	/**
	 *
	 */
	public static final String UPDATE_AGENCY_INFO = "001018";

	/**
	 * 收费项目查询
	 */
	public static final String CHARGES_PROJECTS_QUERY = "001031";

	/**
	 * 更新缴款通知书状态及实缴流水
	 */
	public static final String QUERY_PAY_ACTUAL = "001020";

	/**
	 * 电子缴款书查询
	 */
	public static final String PAYMENTBOOK_QUERYLIST = "005005";

	/**
	 * 对应的汇缴结算户查询接口
	 */
	public static final String ACCREF_NTAGENCY_LIST = "001003";

	/**
	 * 采集 提供版式文件
	 */
	public static final List<String> PROVIDE_BILL_FILE = Arrays.asList("0", "1", "2", "4", "5","6");

	/**
	 * 采集 不提供版式文件
	 */
	public static final List<String> NO_BILL_FILE = Arrays.asList("3");

	/**
	 * 页数
	 */
	public static final Integer PAGE_NO = 1;
	/**
	 * 行数
	 */
	public static final Integer PAGE_SIZE = 10;

	/**
	 * int 0
	 */
	public static final int INT_0  = 0;

	/**
	 * int 1
	 */
	public static final int INT_1  = 1;
	/**
	 * int 2
	 */
	public static final int INT_2  = 2;
	/**
	 * int 3
	 */
	public static final int INT_3  = 3;

	/**
	 * int 4
	 */
	public static final int INT_4  = 4;

	/**
	 * int 5
	 */
	public static final int INT_5  = 5;

	/**
	 * int 6
	 */
	public static final int INT_6  = 6;

	/**
	 * int 8
	 */
	public static final int INT_8  = 8;
	/**
	 * int 9
	 */
	public static final int INT_9  = 9;
	/**
	 * int 10
	 */
	public static final int INT_10  = 10;

	/**
	 * int 11
	 */
	public static final int INT_11  = 11;

	/**
	 * int 12
	 */
	public static final int INT_12  = 12;

	/**
	 * int 20
	 */
	public static final int INT_20  = 20;

	/**
	 * int 28
	 */
	public static final int INT_28  = 28;

	/**
	 * int 29
	 */
	public static final int INT_29  = 29;

	/**
	 * int 30
	 */
	public static final int INT_30  = 30;

	/**
	 * int 31
	 */
	public static final int INT_31  = 31;

	/**
	 * int 100
	 */
	public static final int INT_100  = 100;

	/**
	 * int 200
	 */
	public static final int INT_200  = 200;

	/**
	 * int 400
	 */
	public static final int INT_400  = 400;

	/**
	 * int 1900
	 */
	public static final int INT_1900  = 1900;

	/**
	 * int 2200
	 */
	public static final int INT_2200  = 2200;

	/**
	 * 批量下载 每批条数
	 */
	public static final int BATCH_NUM  = 500;

	/**
	 * 单次最大每页条数
	 */
	public static final Integer MAX_PAGE_SIZE = 500;

	/**
	 * 初始化内存大小 512 * 512
	 */
	public static final Integer SIZE_512_512 = 262144;

	/**
	 * 收项目类别：1非税执收项目；2往来项目；3预收收入项目；4暂扣收入项目
	 */
	public static final Integer PRO_TYPE_INTEGER_1 = 1;

	public static final Integer PRO_TYPE_INTEGER_2 = 2;

	public static final Integer PRO_TYPE_INTEGER_3 = 3;

	public static final Integer PRO_TYPE_INTEGER_4 = 4;

	/**
	 * EXECL xlsx后缀
	 */
	public static final String XLSX = "xlsx";

	/**
	 * EXECL xls后缀
	 */
	public static final String XLS = "xls";

	/**
	 * 成功
	 */
	public static final String  SUCCESS= "success";

	/**
	 * 失败
	 */
	public static final String  ERROR = "error";


	/**
	 * OPD
	 */
	public static final String OFD = "OFD";

	/**
	 * PDF
	 */
	public static final String PDF = "PDF";

	/**
	 * xml格式
	 */
	public static final String XML = "XML";

	/**
	 * OFD.xml
	 */
	public static final String OFD_XML = "OFD.xml";

	/**
	 * GBK编码encoding
	 */
	public static final String GBK = "GBK";

	/**
	 * mdtrtXmlName格式
	 */
	public static final String MDTRT_XML_NAME = "-extinfo.xml";

	/**
	 * -setl.xml
	 */
	public static final String SETL_XML_NAME = "-setl.xml";

	public static final String EXTINFO_XML_NAME = "-extinfo";

	public static final String RE_NAME = "re";

	/**
	 * OFD 隐藏字符串key值
	 */
	public static final String OFD_HIDE_XML = "einvoice.xml";

	/**
	 * PDF 隐藏字符串key值
	 */
	public static final String PDF_HIDE_XML = "hidexml";

	/**
	 * 包含此字符串的文件类型
	 */
	public static final String BILL_FILE_TYPE_PDF = "255044";

	/**
	 * 通用数字-1
	 */
	public static final Integer NUM_COMMON_NEG_1 = -1;

	/**
	 * 通用数字0
	 */
	public static final Integer NUM_COMMON_0 = 0;

	/**
	 * 通用数字1
	 */
	public static final Integer NUM_COMMON_1 = 1;

	/**
	 * 通用数字2
	 */
	public static final Integer NUM_COMMON_2 = 2;

	/**
	 * 通用数字3
	 */
	public static final Integer NUM_COMMON_3 = 3;

	/**
	 * 通用数字4
	 */
	public static final Integer NUM_COMMON_4 = 4;

	/**
	 * 通用数字5
	 */
	public static final Integer NUM_COMMON_5 = 5;

	/**
	 * 通用数字6
	 */
	public static final Integer NUM_COMMON_6 = 6;

	/**
	 * 通用数字7
	 */
	public static final Integer NUM_COMMON_7 = 7;
	/**
	 * 通用数字8
	 */
	public static final Integer NUM_COMMON_8 = 8;
	/**
	 * 通用数字9
	 */
	public static final Integer NUM_COMMON_9 = 9;
	/**
	 * 通用数字9
	 */
	public static final Integer NUM_COMMON_10 = 10;
	/**
	 * 通用数字9
	 */
	public static final Integer NUM_COMMON_12 = 12;
	/**
	 * 通用数字15
	 */
	public static final Integer NUM_COMMON_15 = 15;
	/**
	 * 通用数字18
	 */
	public static final Integer NUM_COMMON_18 = 18;

	/**
	 * 通用数字20
	 */
	public static final Integer NUM_COMMON_20 = 20;

	/**
	 * 通用数字22
	 */
	public static final Integer NUM_COMMON_22 = 22;

	/**
	 * 通用数字100
	 */
	public static final Integer NUM_COMMON_100 = 100;

	/**
	 * 通用数字200
	 */
	public static final Integer NUM_COMMON_200 = 200;

	/**
	 * 通用数字1000
	 */
	public static final Integer NUM_COMMON_1000 = 1000;

	/**
	 * 通用数字10000
	 */
	public static final Integer NUM_COMMON_10000 = 10000;

	/**
	 * 长整型数字1
	 */
	public static final Long NUM_LONG_1 = 1L;

	/**
	 * 门诊
	 */
	public static final String OPT = "门诊";

	/**
	 * 住院
	 */
	public static final String IPT = "住院";



	public static final String GLUE_TYPE_BEAN = "BEAN";

	/**
	 * zip
	 */
	public static final String ZIP = "ZIP";

	/**
	 * 通用数字-1
	 */
	public static final String COMMON_NEG_1 = "-1";

	/**
	 * 通用字符串0
	 */
	public static final String COMMON_0 = "0";

	/**
	 * 通用数字1
	 */
	public static final String COMMON_1 = "1";

	/**
	  * 通用数字2
	  */
	 public static final String COMMON_2 = "2";

	/**
	 * 通用数字3
	 */
	public static final String COMMON_3 = "3";
	/**
	 * 通用数字4
	 */
	public static final String COMMON_4 = "4";
	/**
	 * 通用数字5
	 */
	public static final String COMMON_5 = "5";
	/**
	 * 通用数字6
	 */
	public static final String COMMON_6 = "6";
	/**
	 * 通用数字7
	 */
	public static final String COMMON_7 = "7";
	/**
	 * 通用数字8
	 */
	public static final String COMMON_8 = "8";
	/**
	 * 通用数字9
	 */
	public static final String COMMON_9 = "9";

	/**
	 * 公用jobHandler
	 */
	public static final String COMMON_JOB_HANDLER = "bswJobExeHandler";

	public static final String QUERY_STATUS_JOB_DESC = "查询凭证状态自动任务";

	public static final String QUERY_UPLOAD_JOB_DESC = "验证上传结果频率自动任务";

	public static final String CLCT_BILL_JOB_DESC = "文件夹凭证信息采集自动任务";

	public static final String CLCT_RLTS_JOB_DESC = "文件夹凭证关系采集自动任务";



	/**
	 * 通用字符C
	 */
	public static final String COMMON_C = "C";
	/**
	 * 通用字符T
	 */
	public static final String COMMON_T = "T";
	/**
	 * 通用字符A
	 */
	public static final String COMMON_A = "A";

	/**
	 * V
	 */
	public static final String COMMON_V = "V";

	/**
	 * 通用字符—
	 */
	public static final String CONNECT = "-";

	/**
	 * 空字符串
	 */
	public static final String EMPTYSTRING = " ";
	/**
	 * 通用字符 .
	 */
	public static final String COMMA = ".";

	/**
	 * 带转译字符 .
	 */
	public static final String TRAN_COMMA = "\\.";

	/**
	 * 正则表达式 匹配包含空格的字符串
	 */
	public static final String REGEX_SPACE = ".*\\s.*";

	public static final String FIX_CODE_ERROR = "旧密码输入错误!";

	public static final String CODE_FIX_SUCCESS = "密码重置成功!";

	/**
	 * 版式文件-读取失败原因
	 */
	public static final String SETL_BILL_REPEAT = "版式文件重复上传";

	/**
	 * 版式文件-读取失败原因
	 */
	public static final String SETL_BILL_RESOLVER_ERROR = "版式文件解析失败";

	/**
	 * 版式文件代码-读取失败原因
	 */
	public static final String SETL_BILL_CODE_RESOLVER_ERROR = "文件解析票据代码为空";

	/**
	 * 版式文件号码-读取失败原因
	 */
	public static final String SETL_BILL_NO_RESOLVER_ERROR = "文件解析票据号码为空";


	/**
	 * 异常文件目录
	 */
	public static final String ERROR_FILE = "errorfile";

	/**
	 * 冒号
	 */
	public static final String COLON = ":";

	/**
	 * 分号
	 */
	public static final String SEMICOLON = ";";

	/**
	 * 链接地址版本财政(V1.0)
	 */
	public static final String URL_VERSION_V1 = "1";

	/**
	 * 链接地址版本财政(V2.0)
	 */
	public static final String URL_VERSION_V3 = "3";

	/**
	 * 链接地址版本财政(V3.0)
	 */
	public static final String URL_VERSION_V4 = "4";

	/**
	 * 链接地址版本财政(V4.0)
	 */
	public static final String URL_VERSION_V5 = "5";

	/**
	 * 链接地址税务(V1.0)
	 */
	public static final String URL_DIRECT_VERSION_V1 = "2";


	/**
	 * 从财政端下载版式文件
	 */
	public static final String URL_VERSION_V6 = "6";

	/**
	 * 链接地址版本财政(V5.0)
	 */
	public static final String URL_VERSION_V7 = "7";

	/**
	 * 校验是否归档地址
	 */
	public static final String CHECK_FILED_URL = "/peripheral/queryBill/queryBillInfo.do?ciphertext=";

	/**
	 * 版式文件下载参数
	 */
	public static final String CONTEXT_PATH = "_contextPath";
	public static final String APP_ID = "appId";
	public static final String CIPHER_TEXT = "ciphertext";
	public static final String METHOD = "method";
	public static final String ELEMENT = "script";

	/**
	 * http://
	 */
	public static final String HTTP_PRE = "http://";

	/**
	 * &
	 */
	public static final String HTTP_PARAM_ = "&";

	/**
	 * 医保业务编码库类别
	 */
	public static final String XI_YAO = "XZ";
	public static final String ZI_ZHI_YAO = "J";
	public static final String ZHONG_YAO = "T";
	public static final String HAO_CAI = "CQ";

	/**
	 * 目录名 license
	 */
	public static final String LICENSE = "license";

	public static final String VERSION_2_0 = "2.0";

	public static final String VERSION_2_1 = "2.1";
	/**
	 * 2.2
	 */
	public static final String VERSION_2_2 = "2.2";

	public static final String VERSION_2_3 = "2.3";

	public static final String VERSION_2_4 = "2.4";

	/**
	 * License文件名 license-bsw.lic
	 */
	public static final String LICENSE_DOC_NAME = "license-bsw.lic";

	/**
	 * License文件名 license-bsw.lic
	 */
	public static final String SYS_LICENSE_DOC_NAME = "license-bsw-system.lic";

	/**
	 * 用户的当前工作目录 user.dir
	 */
	public static final String USER_DIR = "user.dir";

	/**
	 * 控件库清库脚本地址
	 */
	public static final String BSW_CLEAR_SCRIPT = "/clearscript/bswClear.sql";

	/**
	 * 中间库清库脚本地址
	 */
	public static final String MID_CLEAR_SCRIPT = "/clearscript/midClear.sql";

	/**
	 * 正则
	 */
	public static final String XML_REG = ".+(-extinfo.xml)$";
	public static final String ZIP_REG = ".+(.zip)$";
	public static final String PDF_OFD_REG = ".+(.pdf|.ofd)$";
	/**
	 * 校验文件开头为re123456格式类型文件
	 */
	public static final String RE_REGEX = "^re.*";


	public static final String PDF_OFD_ZIP = "PDFOFDZIP";

	/**
	 * 凭证关系文件前缀常量
	 */
	public static final String RLTS_XLS_XLSX_REG = "^setlrlts.*.xlsx?$";
	/**
	 * 文件夹模式查询凭证状态文件前缀常量
	 */
	public static final String STATUS_XLS_XLSX_REG = "^status.*.xlsx?$";
	/**
	 * 文件夹模式查询凭证状态文件后缀常量
	 */
	public static final String STATUS_FILE_SUFFIX = "result";

	/**
	 * 文件夹模式查询凭证状态文件已读标识
	 */
	public static final String STATUS_FILE_READ = "read";

	/**
	 * 心跳检测入参常量
	 */
	public static final String CONSTANT_HEARTBEAT = "999999999";
	/**
	 * 票据代码包含0601是门诊，0602是住院
	 */
	public static final String TICKET_TYPE_JUDGE = "0601";

	/**
	 * 等号常量
	 */
	public static final String EQUALS_SIGN = "=";

	/**
	 * 反斜杠常量
	 */
	public static final String BACKSKE = "\"";

	/**
	 * 获取指定HTML标签的指定属性的值正则表达式
	 */
	public static final String GET_HTML_REGEX = "(var.*).*?(?=;)";

	/**
	 * 财政电子票据xml地址
	 */
	public static final String CZ_XML_URL = "/xml/czdzpj.xml";
	/**
	 * 电子凭证xml地址
	 */
	public static final String DZ_XML_URL = "/xml/dzjspz.xml";

	/**
	 * COM组件常量
	 */
	public static final String PROGRAM_ID = "yinhai.yh_hb_sctr";

	public static final String FUNCTION_NAME = "yh_hb_call";

	/**
	 * 两定接口编码：9001
	 */
	public static final String INFNO_9001 = "9001";

	public static final String SIGNINOUTB = "signinoutb";
	/**
	 * 转换Cron表达式常量
	 */
	public static final int CRON_60 = 60;

	public static final int CRON_3600 = 3600;

	public static final int CRON_84400 = 84400;


	//01 门诊
	public static final String MED_TYPE_ONE = "01";
	//02 住院
	public static final String MED_TYPE_TWO = "02";

	/**
	 * EXECL文件名 xlsx后缀
	 */
	public static final String FILE_XLSX = ".xlsx";

	/**
	 * EXECL文件名 xls后缀
	 */
	public static final String FILE_XLS = ".xls";
	/**
	 * 需要清理的EXECL文件名前缀
	 */
	public static final String DELETE_FILE = "readstatus";

	/**
	 * 空字符串
	 */
	public static final String NULL_CHARACTER_STRING = "";

	/**
	 * 空字符串
	 */
	public static final String NULL = "null";

	/**
	 * 小数点后两位
	 */
	public static final String TWO_DECIMAL_PLACES = "0.00";

	/**
	 * 小数点后四位
	 */
	public static final String FOUR_DECIMAL_PLACES = "0.0000";

	/**
	 * 小数点后六位
	 */
	public static final String SIX_DECIMAL_PLACES = "0.000000";


	/**
	 * 版式文件下载地址
	 */
	public static final String PDF_DOWN_URL = "/peripheral/queryBill/downloadFile.do?";


	/**
	 * 票据号码
	 */
	public static final String BATCH_CODE = "fbillBatchCode";

	/**
	 * 票据号码
	 */
	public static final String BILL_NO = "&fbillNo=";

	/**
	 * adapter返回的错误编码
	 */
	public static final String ADAPTER_ERROR_NUM = "A999";

	/**
	 * 正常状态
	 */
	public static final Integer STATUS_NORMAL = 0;

	/**
	 * 禁用状态
	 */
	public static final Integer STATUS_DISABLE = -1;

	/**
	 * 删除标志
	 */
	public static final Integer DEL_FLAG_1 = 1;

	/**
	 * 未删除
	 */
	public static final Integer DEL_FLAG_0 = 0;

	/**
	 * 系统日志类型： 登录
	 */
	public static final int LOG_TYPE_1 = 1;

	/**
	 * 系统日志类型： 操作
	 */
	public static final int LOG_TYPE_2 = 2;

	/**
	 * 操作日志类型： 查询
	 */
	public static final int OPERATE_TYPE_1 = 1;

	/**
	 * 操作日志类型： 添加
	 */
	public static final int OPERATE_TYPE_2 = 2;

	/**
	 * 操作日志类型： 更新
	 */
	public static final int OPERATE_TYPE_3 = 3;

	/**
	 * 操作日志类型： 删除
	 */
	public static final int OPERATE_TYPE_4 = 4;

	/**
	 * 操作日志类型： 倒入
	 */
	public static final int OPERATE_TYPE_5 = 5;

	/**
	 * 操作日志类型： 导出
	 */
	public static final int OPERATE_TYPE_6 = 6;


	/** {@code 500 Server Error} (HTTP/1.0 - RFC 1945) */
	public static final String SC_INTERNAL_SERVER_ERROR_500 = "500";
	/** {@code 200 OK} (HTTP/1.0 - RFC 1945) */
	public static final String SC_OK_200 = "200";

	/**
	 *  0：一级菜单
	 */
	public static final Integer MENU_TYPE_0  = 0;
	/**
	 *  1：子菜单
	 */
	public static final Integer MENU_TYPE_1  = 1;
	/**
	 *  2：按钮权限
	 */
	public static final Integer MENU_TYPE_2  = 2;

	/**
	 * 状态(0无效1有效)
	 */
	public static final String STATUS_0 = "0";
	public static final String STATUS_1 = "1";


	/**
	 * 员工身份 （1:普通员工  2:上级）
	 */
	public static final Integer USER_IDENTITY_1 = 1;
	public static final Integer USER_IDENTITY_2 = 2;

	/** sys_role 表 code 唯一键索引 */
	public static final String SQL_INDEX_UNIQ_SYS_ROLE_CODE = "uniq_sys_role_role_code";


	/***************************************************************************/


	public static final String FORMAT_JSON = "json";


	//*****签名状态*****//
	/**签名状态-签名失败*/
	public static final Integer SIGN_STATUS_FAIL = -1;
	/**签名状态-未签名*/
	public static final Integer SIGN_STATUS_NO = 0;
	/**签名状态-已签名*/
	public static final Integer SIGN_STATUS_YES = 1;


	//***财政端开票状态*****//
	/**财政端开票状态-开票失败*/
	public static final Integer FINANCE_STATUS_FAIL = -1;
	/**财政端开票状态-开票中*/
	public static final Integer FINANCE_STATUS_ING = 0;
	/**财政端开票状态-开票成功*/
	public static final Integer FINANCE_STATUS_SUCCESS = 1;
	/**财政端开票状态-重复开票*/
	public static final Integer FINANCE_STATUS_REPEAT = 2;

	/**系统缓存参数表编码**/
	/**接口版本号**/
	public static final String INTERFACE_VERSION = "interface_version";
	/**接口请求格式**/
	public static final String INTERFACE_FORMAT = "json";
	/**下载pdf失败 是狗生成无签名pdf**/
	public static final String IS_RETURN_NO_SIGN_PDF = "isReturnNoSignPdf";

	/**财政印章信息**/
	public static final String FINANCE_SEAL = "finance_seal";
	/**XML版本号**/
	public static final String XML_VERSION = "xml_version";
	/**电子票据监管机构代码**/
	public static final String SUPERVISOR_AREACODE = "supervisor_areacode";
	/**是否启用票据管理 0否1是**/
	public static final String TICKET_MANAGEMENT = "ticket_management";
	/**是否启用消息推送 0否1是**/
	public static final String MESSAGE_PUSH = "message_push";
	/**是否启用票据管理 是**/
	public static final String TICKET_MANAGEMENT_YES = "1";
	/**是否启用票据管理 否**/
	public static final String TICKET_MANAGEMENT_NO = "0";
	/**是否启用密钥 0否1是**/
	public static final String IS_SECRET_KEY = "is_secret_key";
	/**交款人代码是否脱敏1：是；2:否；默认为1**/
	public static final String IS_DESENS = "is_desens";
	/**收款账号是否脱敏 0：不脱敏；1:收款账号脱敏；默认为0**/
	public static final String DESENS_TYPE = "desens_type";
	/**
	 * 是否通过支付宝渠道推送电子票据 1是 2否 默认为否
	 */
	public static final String IS_ALIPAY_PUSH = "is_alipay_push";
	/**是否校验结算关系:0不校验,1校验**/
	public static final String IS_CHECK_SETL = "is_check_setl";
	/**是否启用密钥管理 是**/
	public static final String IS_SECRET_KEY_YES = "1";
	/**开票员同步请求路径**/
	public static final String TICKETER_SYNCHRONOUS_URL = "ticketer_synchronous_url";
	/**开票员映射参数**/
	public static final String TICKETER_MAPPING_PARAME = "ticketer_mapping_parame";
	/**开票员同步单位编码**/
	public static final String SYNCHRONOUS_AGENCY_CODE = "synchronous_agency_code";


	/**財政印章信息常量***/
	/**印章编号**/
	public static final String SEALID = "SealId";
	/**印章名称**/
	public static final String SEALNAME = "SealName";
	/**印章Hash**/
	public static final String SEALHASH = "SealHash";
	/**印章图片Base64**/
	public static final String SEALBASE64 = "SealBase64";
	/**区划编码**/
	public static final String ADMDIVCODE = "admdiv_code";
	/**单位/票据代码 分隔符**/
	public static final String TICKET_SEPARATOR = ":";



	/**1已上传**/
	public static Integer IS_SEND_YES = 1;
	/**0未上传 **/
	public static Integer IS_SEND_NO = 0;
	/**1已冲红**/
	public static Integer IS_RUSH_YES = 1;
	/**0 未冲红**/
	public static Integer IS_RUSH_NO = 0;
	/**1 有效**/
	public static Integer IS_VALID_YES = 1;
	/**0 无效**/
	public static Integer IS_VALID_NO = 0;
	/**1 已换开**/
	public static Integer iS_SWITCH_YES = 1;
	/**0 未换开**/
	public static Integer IS_SWITCH_NO = 0;
	/**票据类型 0 正常票**/
	public static Integer TICKET_TYPE_NORMAL = 0;
	/**票据类型 1 红冲票**/
	public static Integer TICKET_TYPE_RUSH = 1;
	/**票据类型 2 纸质票**/
	public static Integer TICKET_TYPE_PAPER = 2;

	/** 是否欠缴0是 1否（默认为1）个人欠缴的票据不通知推送  是否欠缴 **/
	public static Integer IS_ARREAR_NO = 0;
	/** 是否欠缴0是 1否（默认为1）个人欠缴的票据不通知推送  是否欠缴 **/
	public static Integer IS_ARREAR_YES = 1;

	/**是否核销0否1是**/
	public static Integer IS_CANCEL_NO = 0;
	/**是否核销0否1是**/
	public static Integer IS_CANCEL_YES = 1;


	/**类型 1门诊**/
	public static Integer MEDICAL_TYPE_OUTPATIENT = 1;
	public static String MEDICAL_TYPE_OUTPATIENT_NAME = "门诊票据";
	/**类型 2住院**/
	public static Integer MEDICAL_TYPE_HOSPITALIZATION = 2;
	public static String MEDICAL_TYPE_HOSPITALIZATION_NAME = "住院票据";
	/**类型 3其它**/
	public static Integer MEDICAL_TYPE_OTHER = 3;
	public static String MEDICAL_TYPE_OTHER_NAME = "其他票据";
	/**
	 * 启用
	 */
	public static Integer ENABLE_1=1;
	/**
	 * 禁用
	 */
	public static Integer PROHIBIT_0=0;
	/**
	 * 模板文件类型：doc
	 */
	public static String TEMPLATE_TYPE_DOC ="doc";

	/**
	 * 票据模版类型：主单
	 */
	public static String MAIN ="1";
	/**
	 * 票据模版类型：明细单
	 */
	public static String DETAIL ="2";
	/**
	 * 票据模版类型：主单+明细单
	 */
	public static String MAIN_DETAIL ="3";

	/**
	 * 签名算法
	 */
	public static String SIGNATUREALGORITHM = "signaturealgorithm";
	/**
	 * 签名格式类型
	 */
	public static String SIGNATUREFORMAT = "DETACH";
	/**
	 * 医疗类别 - 门诊
	 */
	public static String MEDICALTYPE_OUTPATIENT = "门诊";
	/**
	 * 医疗类别 - 住院
	 */
	public static String MEDICALTYPE_INHOSPITAL = "住院";
	/**
	 * 医疗类型字典编码
	 */
	public static String BUSINESS_TYPE_QUERY = "businessTypeQuery";

	/**
	 * 签名字符集
	 */
	public static final String SIGN_CHARSET_NAME = "GB18030";
	/**
	 * 通用字符集
	 */
	public static final String GENERAL_CHARSET_NAME = "UTF-8";

	/**财政成功回执*/
	public static final String FINANCE_API_SUCC_CODE = "200";

	/**
	 * 批量接收电子票据信息
	 */
	public static final String GETRECEIVING_ELECTRONIC_BILLS_IN_BATCH = "batchQueryEticketInfo";
	/**
	 * 配置信息获取
	 */
	public static final String GETCONFIG_INTERFACE_NAME = "getConfig";
	/**
	 * 查询电子票状态
	 */
	public static final String QUERYETICKETSTATUS_INTERFACE_NAME = "queryETicketStatus";
	/**
	 * 开具电子票据
	 */
	public static final String INVOICEETICKET_NAME = "invoiceEticket";
	/**
	 * 开具电子票据(医疗)
	 */
	public static final String INVOICEETICKET_INTERFACE_NAME = "medicalInvoiceEticket";

	/**
	 * 开具门诊电子票据(医疗-云签)
	 */
	public static final String MEDICAL_OUTPATIENT_INVOICE_ETICKET = "medicalOutpatientInvoiceEticket";

	/**
	 * 开具住院电子票据(医疗-云签)
	 */
	public static final String MEDICAL_HOSPITAL_INVOICE_ETICKET = "medicalHospitalInvoiceEticket";
	/**
	 * 开具红票门诊电子票据(医疗-云签)
	 */
	public static final String MEDICAL_OUTPATIENT_INVOICE_ETICKET_RUSH = "medicalOutpatientInvoiceEticketRush";

	/**
	 * 开具红票住院电子票据(医疗-云签)
	 */
	public static final String MEDICAL_HOSPITAL_INVOICE_ETICKET_RUSH = "medicalHospitalInvoiceEticketRush";


	/**
	 * 批量开具电子票据
	 */
	public static final String BATCHINVOICEETICKET_INTERFACE_NAME = "medicalBatchInvoiceEticket";
	/**
	 * 冲红电子票据
	 */
	public static final String COMMONWRITEOFFETICKET_INTERFACE_NAME = "writeOffEticket";
	/**
	 * 冲红电子票据(医疗)
	 */
	public static final String WRITEOFFETICKET_INTERFACE_NAME = "medicalWriteOffEticket";
	/**
	 * 换开电子票据
	 */
	public static final String RELATEETICKET_INTERFACE_NAME = "relateEticket";
	/**
	 * 纸质门诊票据上传
	 */
	public static final String UPLOADOUTPPAPERTICKET_INTERFACE_NAME = "sendOutpBillInfo";
	/**
	 * 纸质住院票据上传
	 */
	public static final String UPLOADHOSPPAPERTICKET_INTERFACE_NAME = "sendHospBillInfo";
	/**
	 * 开票状态查询
	 */
	public static final String QUERYUPLOADSTATUS_INTERFACE_NAME = "queryUploadStatus";

	/**
	 * 单位预发赋码
	 */
	public static final String PREETICKETNO_INTERFACE_NAME ="preETicketNoQuery";
	/**
	 * 通知电子票据
	 */
	public static final String NOTIFYETICKET_INTERFACE_NAME ="notifyETicket";

	/**
	 * 单位项目信息获取
	 */
	public static final String SYN_PROJECTS = "synProjects";

	/**
	 * 单位信息获取
	 */
	public static final String SYN_AGENCYS = "synAgencys";

	/**
	 * 电子票据信息上传接口
	 */
	public static final String BILL_AGENCY_SIGN = "billagencysign";

	/**开票信息查询*/
	public static final String QUERY_ETICKETINFO = "queryETicketInfo";

	/**
	 * 缴款通知单信息上传
	 */
	public static final String NT_NOTICE_ADD_INFO = "ntNoticeAddInfo";

	/**
	 * 缴款通知单修订信息上传接口
	 */
	public static final String NT_NOTICE_UPDATE_INFO = "ntnoticeupdateinfo";

	/**
	 * 撤销缴款通知单信息接口
	 */
	public static final String NT_NOTICE_DEL_INFO = "ntnoticedelinfo";

	/**
	 * 核销查询
	 */
	public static final String TICKET_WRITE_OFF_QUERY = "ticketWriteOffQuery";

	/**票据预览文件类型-图片*/
	public static final int FILE_TYPE_IMG = 1;
	/**票据预览文件类型-PDF*/
	public static final int FILE_TYPE_PDF = 2;


	/**医疗结构类型*/
	public static final String DICT_MEDICALINSTITUTIONTYPE = "agencyKind";
	/**医保类型*/
	public static final String DICT_MEDICALINSURANCETYPE = "medicalInsuranceType";
	/**缴款方式*/
	public static final String DICT_PAYMODE = "payMode";

	/**获取基础配置信息常量 start**/
	/**成功信息**/
	public static final String CONFIG_MESSAGE = "message";

	/**印章类型 0财政**/
	public static final String SEAL_TYPE_0 = "0";
	/**印章类型 1单位**/
	public static final String SEAL_TYPE_1 = "1";
	/**文件类型**/
	public static final String CONFIG_FILETYPE = "jasper";

	/**开票方式-手工开票*/
	public static final String BILLING_METHOD_MANUAL = "1";
	/**开票方式-机打开票*/
	public static final String BILLING_METHOD_MACHINE = "2";
	/**开票方式-电子开票*/
	public static final String BILLING_METHOD_ELECTRONIC = "3";

	/**财政端票据换开状态码-成功*/
	public static final int RELATEETICKET_STATUS_SUCCESS = 1;
	/**财政端票据换开状态码-失败*/
	public static final int RELATEETICKET_STATUS_FAIL = -1;


	/**批量上传常量 end**/

	/** 财政电子票据状态查询接口-是否开具字段名 */
	public static final String TICKETSTATUS_INVOICE = "invoice";
	/** 财政电子票据状态查询接口-是否开具-是 */
	public static final String TICKETSTATUS_INVOICE_YES = "1";
	/** 财政电子票据状态查询接口-是否开具-否 */
	public static final String TICKETSTATUS_INVOICE_NO = "0";
	/** 电子票据状态查询-是否冲红字段名 */
	public static final String TICKETSTATUS_WRITEOFF = "writeoff";
	/** 电子票据状态查询-是否换开字段名 */
	public static final String TICKETSTATUS_PRINTED = "printed";

	/** 数据核对接口每页记录数 */
	public static int DATAVERIFY_PAGE_SIZE = 100;

	/** 入账状态-未入账 */
	public static String ACC_STATE_NO = "0";
	/** 入账状态-已汇总 */
	public static String ACC_STATE_SUMMARY = "1";
	/** 入账状态-已入账 */
	public static String ACC_STATE_YES = "2";
	/** 入账状态-已归档 */
	public static String ACC_STATE_ARCHIVES = "3";
	/** 入账状态-已上传  ---- 上传功能已废弃，留着常量是因为代码暂不删除 */
	public static String ACC_STATE_UPLOAD = "4";

	/** 汇总单入账状态-未入账 */
	public static String SUM_ACCSTATE_NO = "0";
	/** 汇总单入账状态-已入账 */
	public static String SUM_ACCSTATE_YES = "1";
	/** 汇总单入账状态-已归档 */
	public static String SUM_ACCSTATE_ARCHIVES = "2";
	/** 汇总单入账状态-已上传 -- 上传功能以废弃，暂不删代码 */
	public static String SUM_ACCSTATE_UPLOAD = "3";

	/**批量接收电子票据json格式文件后缀*/
	public static String DOT_JSON = ".json";
	/**批量接收电子票据json格式文件名称*/
	public static String BATCH_RECEIVE_ZIP_JSON_NAME="zipJsonName";
	/**批量接收电子票据zip文件名称*/
	public static String BATCH_RECEIVE_ZIP_FILE_NAME="zipFilename";
	/**批量接收电子票据其相关解压文件信息*/
	public static String BATCH_RECEIVE_ZIP_FILE_INFO="zipFile";
	/**批量接收电子票据其相关解压的JSON字符串中的"票据票号"字段*/
	public static String BATCH_RECEIVE_ZIP_JSON_EINVOICE_NUMBER="EInvoiceNumber";
	/**批量接收电子票据其相关解压的JSON字符串中的"票据代码"字段*/
	public static String BATCH_RECEIVE_ZIP_JSON_EINVOICE_CODE="EInvoiceCode";
	/**
	 * xml文件后缀
	 */
	public static String DOT_XML = ".xml";

	/**
	 * xml字符串
	 */
	public static String STR_XML = "xml";

	/**
	 * pdf文件后缀
	 */
	public static String DOT_PDF = ".pdf";

	/**
	 * png文件后缀
	 */
	public static String DOT_PNG = ".png";

	/**
	 * 明细pdf文件后缀
	 */
	public static String DETAIL_DOT_PDF = "detail.pdf";

	/** 会计凭证号归档状态 - 未归档  */
	public static String ARCHIVES_STATE_NO = "0";
	/** 会计凭证号归档状态 - 已归档  */
	public static String ARCHIVES_STATE_YES = "1";



	/********************票据领用 - start ********************/
	/** 领用状态 ---- 0未确认  */
	public static int RECEIVE_STATUS_NO = 0;
	/** 领用状态 ---- 1已确认  */
	public static int RECEIVE_STATUS_YES = 1;
	/** 领用状态 ---- 2未提交 */
	public static int RECEIVE_STATUS = 2;

	/********************票据领用 - end ********************/

	/** 角色类型 0系统管理员 **/
	public static String ROLE_TYPE_SYSADM = "0";
	/** 角色类型 1票据管理员 **/
	public static String ROLE_TYPE_TICKETADM = "1";
	/** 角色类型 2开票员 **/
	public static String ROLE_TYPE_TICKETER = "2";

	/** 角色类型 4医院/学校会计 **/
	public static String ROLE_TYPE_ACCOUNTING = "4";
	/**
	 * 入库状态：出库（下发、分发、退回）
	 */
	public static String OUT_STORAGE="0";
	/**
	 * 入库状态：入库（入库、退回确认）
	 */
	public static String PUT_STORAGE="1";
	/**
	 * 库存类型：0单位
	 */
	public static String STORAGE_TYPE_AGENCY = "0";
	/**
	 * 库存类型：1用户
	 */
	public static String STORAGE_TYPE_USER = "1";
	/**
	 * 单位类型:0 单位
	 * */
	public static String AGENCY_TYPE_COMPANY="1";
	/**
	 * 单位类型:1 科室
	 * */
	public static String AGENCY_TYPE_DEPARTMENT="2";
	/**
	 * 单位类型:2 开票点
	 */
	public static String AGENCY_TYPE_INVOICE="3";

	/**预发赋码 赋码类型 接口 0**/
	public static int PRECODING_TYPE_0 = 0;
	/**预发赋码 赋码类型 页面 1**/
	public static int PRECODING_TYPE_1 = 1;

	/** 批量接收电子票据-接收类型-手动 */
	public static int BATCHARECEIVE_RECEIVETYPE_MANUAL = 0;

	/** 批量接收电子票据-接收类型-自动 */
	public static int BATCHARECEIVE_RECEIVETYPE_AUTO = 1;

	/** 入账相关信息**/
	public static String ACCOUNT_INFO ="account_info";
	/** 是否入账**/
	public static String IS_ACCOUNTED ="is_accounted";

	/**业务标识01住院**/
	public static String BUSINESS_TYPE_01 = "01";
	/**业务标识02门诊**/
	public static String BUSINESS_TYPE_02 = "02";
	/**业务标识03急诊**/
	public static String BUSINESS_TYPE_03 = "03";
	/**业务标识04门特**/
	public static String BUSINESS_TYPE_04 = "04";
	/**业务标识05体检**/
	public static String BUSINESS_TYPE_05 = "05";
	/**业务标识06住院结算**/
	public static String BUSINESS_TYPE_06 = "06";
	/**业务标识07门诊结算**/
	public static String BUSINESS_TYPE_07 = "07";

	/**医保结算标识 0是未结算1是已结算**/
	public static Integer IS_SETTLE_NORMAL = 0;
	/**医保结算标识 0是未结算1是已结算**/
	public static Integer IS_SETTLE_INSURANCE= 1;

	/** 消息通知方式  1 短信，2邮件，5支付宝，3微信  **/
	public static String SEND_TYPE_1 = "1";
	public static String SEND_TYPE_2 = "2";
	public static String SEND_TYPE_3 = "3";
	public static String SEND_TYPE_5 = "5";
	public static String MSG_TYPE_06 = "06";

	/** 是否发送通知 1是发送 0是不发送  **/
	public static String IS_SEND_MESSAGE_1 = "1";
	public static String IS_SEND_MESSAGE_0 = "0";

	/**医疗门诊ticketCode */
	public static final String MEDICAL_OUTPATIENT="430601";
	/**医疗住院ticketCode */
	public static final String MEDICAL_INHOSPITAL="430602";

	/**
	 * 是否确认 0是未确认 1是已确认
	 */
	public static final String IS_CONFIM_0= "0";
	public static final String IS_CONFIM_1 = "1";
	/**是否领票单位（0否 1是） */
	public static final int IS_TICKET_AGENCY_1=1;

	/**医疗住院号规则 */
	public static final String MEDICAL_INHOSPITAL_RULE="z";
	/**医疗门诊号规则 */
	public static final String MEDICAL_OUTPATIENT_RULE="m";

	/**
	 * 通用4(String)
	 */
	public static final String COMMON_STR_4 = "4";

	/**
	 * 通用3(String)
	 */
	public static final String COMMON_STR_3 = "3";

	/**
	 * 通用2(String)
	 */
	public static final String COMMON_STR_2 = "2";

	/**
	 * 通用1(String)
	 */
	public static final String COMMON_STR_1 = "1";

	/**
	 * 通用0(String)
	 */
	public static final String COMMON_STR_0 = "0";



	/**
	 * 通用0
	 */
	public static final Integer INTEGER_COMMON_0 = 0;

	/**
	 * 通用0 - long
	 */
	public static final long COMMON_LONG_0 = 0;

	/** 票据类型标识编号*/
	public static final String TICKET_TYPE_CODE="3";

	/**
	 * 发放记录：退回 1
	 */
	public static final int BACKE_1 = 1;
	/**
	 * 发放记录：领用 0
	 */
	public static final int BACKE_0 = 0;

	/**
	 * 入库状态：0未入库1已入库
	 */
	public static final int STATUS_BACK_0 = 0;
	/**
	 * 入库状态：0未入库1已入库
	 */
	public static final int STATUS_BACK_1 = 1;
	/**
	 * 是否退回：1 退回，0 领用
	 */
	public static final int IS_BACK = 1;
	/**
	 * *节点状态 默认为0，1保存票据信息异常、2 初始化调用财政信息异常 3  调用财政接口异常 4  回执信息异常 5 处理成功
	 */
	public static final int INVOICE_TASK_0 = 0;
	public static final int INVOICE_TASK_1 = 1;
	public static final int INVOICE_TASK_2 = 2;
	public static final int INVOICE_TASK_3 = 3;
	public static final int INVOICE_TASK_4 = 4;
	public static final int INVOICE_TASK_5 = 5;
	/**
	 *   * 异常任务类型 0默认，1 人工干预 2 自动处理（只有TASK_STATE 为 3、4时）
	 */
	public static final int INVOICE_ERROR_TASK_0 = 0;
	public static final int INVOICE_ERROR_TASK_1 = 1;
	public static final int INVOICE_ERROR_TASK_2 = 2;


	/**
	 *   * 异常任务类型 异常处理状态 0默认  1 处理中  2 处理成功  3处理失败    针对财政回执异常的数据与TASK_TYPE有关系
	 */
	public static final int TASK_STATUS_3 = 3;
	public static final int TASK_STATUS_2 = 2;
	public static final int TASK_STATUS_1 = 1;
	public static final int TASK_STATUS_0 = 0;

	/**
	 * 缴款类型：0直接缴款；1集中汇缴；2电子票换开
	 */
	public static final int PAY_TYPE_0 = 0;
	/**
	 * 缴款类型：0直接缴款；1集中汇缴；2电子票换开
	 */
	public static final int PAY_TYPE_1 = 1;
	/**
	 * 缴款类型：0直接缴款；1集中汇缴；2电子票换开
	 */
	public static final int PAY_TYPE_2 = 2;

	/**是否为红票：0不是，1是 */
	public static final String IS_RED_0="0";


	/**业务类型 0门诊 1住院 2体检 3门诊医保结算 4、住院医保结算*/
	public static int BIZ_TYPE_0 = 0;
	/**业务类型 0门诊 1住院 2体检 3门诊医保结算 4、住院医保结算*/
	public static int BIZ_TYPE_1 = 1;
	/**业务类型 0门诊 1住院 2体检 3门诊医保结算 4、住院医保结算*/
	public static int BIZ_TYPE_2 = 2;
	/**业务类型 0门诊 1住院 2体检 3门诊医保结算 4、住院医保结算*/
	public static int BIZ_TYPE_3 = 3;
	/**业务类型 0门诊 1住院 2体检 3门诊医保结算 4、住院医保结算*/
	public static int BIZ_TYPE_4 = 4;

	/**是否批量，默认0 批量处理后为1*/
	public static int IS_BATCH_DEAL_1 = 1;
	/**是否批量，默认0 批量处理后为1*/
	public static int IS_BATCH_DEAL_0 = 0;


	/**返回成功 已经换开过（流水号、纸质票、电子票都相等）**/
	public static final String RESULT_1 = "1";
	/**重复换开**/
	public static final String RESULT_2 = "2";
	/**换开**/
	public static final String RESULT_3 = "3";
	/**换开 只调用财政端**/
	public static final String RESULT_4 = "4";
	/**是否作废 0 否 1**/
	public static Integer IS_VALID_0 = 0;
	/**是否作废 0 否 1是**/
	public static Integer IS_VALID_1 = 1;

	/**票据类型 1 纸质票**/
	public static Integer TICKET_TYPE_PAPER_1 = 1;

	/**纸质票**/
	public static Integer TICKET_TYPE_2 = 2;

	/**是否以https方式请求 0否 1是**/
	public static Integer COMMON_INTEGER_0 = 0;

	/**连接超时时间**/
	public static Integer CONNECT_TIMEOUT = 3600000;

	/**读取超时时间**/
	public static Integer READ_TIMEOUT = 3600000;

	/** 财政部批量开票每批次最大限制 */
	public static int FIN_INVOICE_PAGE_SIZE = 500;

	/** 常量 财政部 */
	public static String TREASURY = "treasury";
	/** 电票系统 */
	public static String NSCS = "nscs";
	/** 湖南 */
	public static String DEFAULT = "default";

	/** 常量 空字符串 */
	public static String EMPTY = "";

	/**
	 * 消息摘要计算方式为SM3
	 */
	public static final String SM3 = "SM3";

	/**
	 * 通用数字10
	 */
	public static final Integer NUM_COMMON_256 = 256;
	/**
	 * 定时任务 默认执行器策略
	 */
	public static final String JOB_EXECUTOR_ROUTE_STRATEGY = "FIRST";

	/**
	 * 定时任务 默认阻塞处理策略
	 */
	public static final String JOB_EXECUTOR_BLOCK_STRATEGY = "DISCARD_LATER";

	/**
	 * 定时任务 默认GLUE类型
	 */
	public static final String JOB_GLUE_TYPE = "BEAN";

	/**
	 * 票据代码包含0601是门诊，0602是住院
	 */
	public static final String TICKET_TYPE_0601 = "0601";

	/**
	 * 票据代码包含0601是门诊，0602是住院
	 */
	public static final String TICKET_TYPE_0602 = "0602";

	/**
	 * 对接财政标识
	 */
	public static final String INTERFACE = "treasury";

	/**
	 * 图片格式
	 */
	public static final String PNG_TYPE = "PNG";

	/**
	 * pdf像素
	 */
	public static final Integer PDF_DPI = 144;

	/**
	 * ofd像素
	 */
	public static final Integer OFD_PPM = 15;

	/**
	 * 类型头部
	 */
	public static final String FILE_IMAGE = "data:image/png;base64,";

	/**
	 * 包含此字符串的文件类型
	 */
	public static final String BILL_FILE_TYPE_OFD = "504B03";

	/**
	 * 包含此字符串的文件类型
	 */
	public static final String BILL_FILE_TYPE_XML = "3C3F78";

	/**
	 * appkey
	 */
	public static final String APP_KEY = "app_key";

	public static final String BSW_SERVER_PERMISSION_ID = "1";

	public static final String BSW_INVOICE_PERMISSION_ID = "2";

	public static final String BSW_SETLDOC_PERMISSION_ID = "5";

	public static String FIELD_PAGE_SIZE = "page_size";

	public static Integer COMMON_500 = 500;

	/**
	 * bsw版本号字典code
	 */
	public static final String BSW_VERSION = "bswVersion";

	/**
	 * 公用数字23
	 */
	public static Integer COMMON_INT_23 = 23;

	/**
	 * 公用数字59
	 */
	public static Integer COMMON_INT_59 = 59;

	/**
	 * 公用数字20
	 */
	public static Integer COMMON_INT_20 = 20;

	/**
	 *字符类型 "-"
	 */
	public static final String SYMBOL_TYPE_LINE ="-";

	/**
	 * 公用数字1
	 */
	public static final Integer INTEGER_COMMON_1 = 1;

	/**
	 * 公用数字3
	 */
	public static final Integer INTEGER_COMMON_3 = 3;

	/**
	 * 公用数字4
	 */
	public static final Integer INTEGER_COMMON_4 = 4;

	/**
	 * 公用数字1024
	 */
	public static final Integer INTEGER_COMMON_1024 = 1024;

	/**
	 * 斜杠
	 */
	public static final String SEPARATOR = "/";

	/**
	 * 保存版式文件方式为保存到数据库表中
	 */
	public static final String TICKET_TYPE_SETLDOC = "SETLDOC";

	/**
	 * 保存版式文件方式为保存到数据库表中
	 */
	public static final String BILL_FILE = "billfile";

	/**
	 * 湖南两定接口请求头_api_name字段
	 */
	public static final String API_NAME = "_api_name";

	/**
	 * 湖南两定接口请求头_api_version字段
	 */
	public static final String API_VERSION ="_api_version";

	/**
	 * 湖南两定接口请求头_api_access_key字段
	 */
	public static final String API_ACCESS_KEY = "_api_access_key";

	/**
	 * 湖南两定接口请求头_api_secreKey字段
	 */
	public static final String API_SECREKEY = "_api_secreKey";

	/**
	 * 湖南两定接口请求头_api_timestamp字段
	 */
	public static final String API_TIMESTAMP = "_api_timestamp";

	/**
	 * 湖南两定接口请求头_api_signature字段
	 */
	public static final String API_SIGNATURE = "_api_signature";

	/**
	 * 	等号
	 */
	public static final String EQUAL_MARK = "=";

	/**
	 * 	&符号
	 */
	public static final String AND_MARK = "&";

	/**
	 * GMT+8
	 */
	public static final String TIME_ZONE = "GMT+8";

	/**
	 * 广东两定机构调用广东医保请求头-应用账户编码字段
	 */
	public static final String X_TIF_PAASID = "x-tif-paasid";

	/**
	 * 广东两定机构调用广东医保请求头-签名字段
	 */
	public static final String X_TIF_SIGNATURE = "x-tif-signature";

	/**
	 * 广东两定机构调用广东医保请求头-时间字段
	 */
	public static final String X_TIF_TIMESTAMP = "x-tif-timestamp";

	/**
	 * 广东两定机构调用广东医保请求头-校验码字段
	 */
	public static final String X_TIF_NONCE = "x-tif-nonce";

	/**
	 * SECRET_KEY
	 */
	public static final String SECRET_KEY = "secretKey";

	/**
	 * 云南扩展字段key
	 */
	public static final String INFO_SYSCODE = "infosyscode";

	/**
	 * 斜杠/
	 */
	public static final String CHAR_CODE = "/";

	/**
	 * traceId
	 */
	public static final String TRACE_ID_KEY = "traceId";

	/**
	 * 系统配置参数: 是否校验开票员所属机构
	 */
	public static final String CHECK_ORGANIZATION = "check_organization";

	/**
	 * 票据模板 EInvoiceSpecimenCode 标签
	 */
	public static final String INVOICE_SPECIMEN_CODE = "EInvoiceSpecimenCode";

	/**
	 * 票据模板 Version 标签
	 */
	public static final String VERSION = "Version";

	/**
	 * 票据模板 EInvoiceTag 标签
	 */
	public static final String INVOICE_TAG = "EInvoiceTag";

	/**
	 * 票据模板 SealName 标签
	 */
	public static final String SEAL_NAME = "SealName";

	/**
	 * 票据模板 SealId 标签
	 */
	public static final String SEAL_ID = "SealId";

	/**
	 * 票据模板 SupervisorPartySeal 标签
	 */
	public static final String SUPERVISOR_PARTY_SEAL = "SupervisorPartySeal";

	/**
	 * 票据模板 InvoicingPartySeal 标签
	 */
	public static final String INVOICING_PARTY_SEAL = "InvoicingPartySeal";

	/**
	 * 汉字无
	 */
	public static final String STR_NOT = "无";

	/**
	 * 系统默认授权对象
	 */
	public static final String AUTHOBJ_SYS = "system";
	/**
	 * 系统默认授权对象名称
	 */
	public static final String AUTHOBJ_SYS_NAME = "系统级";
	/**成功编码*/
	public static final String SUCCESS_CODE = "0000";

	/**逗号 , */
	public static final String COMMON_COMMA = ",";

	/**资金性质名称 */
	public static final String FUND_TYPE_NAME = "VD10001";



}

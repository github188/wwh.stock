package com.skoo.autocode.conf;


/**
 * <p>
 * 内容摘要: 代码生成单元，表示需要生成的组件代码，如dao、service等
 * </p>
 * <p>
 * 完成日期: 2013年9月7日 下午5:13:03
 * </p>
 * <p>
 * 修改记录:
 * </p>
 * <p/>
 * <pre>
 *    修改日期:
 *    修 改 人:
 *    修改内容:
 * </pre>
 *
 * @author gomiten@163.com
 */
public class GeneratorConfig {

    public static final String TYPE_DOMAIN = "domain";
    public static final String TYPE_XML = "xml";
    public static final String TYPE_SERVICE = "service";
    public static final String TYPE_ACTION = "action";
    public static final String TYPE_PAGE = "page";

    private String type;
    private String targetPackage;
    private String template;

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     */

    public GeneratorConfig() {
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

//	public String getPath(String className) {
//		StringBuffer buf = new StringBuffer();
//		buf.append(targetProject).append("/")
//				.append(StringUtil.packge2path(targetPackage)).append("/");
//		if ("page".equals(type)) {
//			buf.append(new SEOActionNameBuilder("true", "-").build(className));
//		} else {
//			buf.append(className);
//		}
//		buf.append(ConfUtil.type2Suffix(type));
//		return buf.toString();
//	}

}

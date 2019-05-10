package MyCrawler;

public class Test {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	String detail="问题分析：根据你描述的症状上来看，考虑这是由湿疹导致的，与环境，季节、饮食，卫生，温度, 情绪，运动，虫子叮咬，接触的物品等因素有关指导建议：建议你外用丹皮酚软膏治疗，平时母体要饮食清淡，避免辛辣刺激性食物。";
	String substr1=detail.substring(0,detail.indexOf("指asdf导建议"));
	String substr2=detail.substring(detail.indexOf("指asd导建议"));
	System.out.println(substr1);
	System.out.println(substr2);

	}
}

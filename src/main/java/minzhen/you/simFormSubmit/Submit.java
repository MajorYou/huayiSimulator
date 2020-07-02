package minzhen.you.simFormSubmit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class Submit {
	private List<String> answerList = new ArrayList<>();
	private Map<String, String> answerMap = new HashMap<>();
	
	public void htmlSubmit(int pId,int courseId,String cookie,String loginId) throws Exception {
		WebClient webClient = new WebClient(BrowserVersion.CHROME); // 实例化Web客户端
		webClient.getCookieManager().setCookiesEnabled(true);
		webClient.getOptions().setJavaScriptEnabled(false);// 开启js解析。对于变态网页，这个是必须的
		webClient.getOptions().setCssEnabled(false);// 开启css解析。对于变态网页，这个是必须的。
		webClient.getCookieManager()
				.addCookie(new Cookie("sqpx.91huayi.com", "ASP.NET_SessionId", cookie));
		webClient.getCookieManager().addCookie(new Cookie("sqpx.91huayi.com","login_log_id",loginId));

//		autoExam(webClient);
		
		try {
			String url = "http://sqpx.91huayi.com/StudyCenter/Test.aspx?test=y&biaoshi=xc&courseid="+courseId+"&pid="+pId;
			System.out.println(url);
			HtmlPage page = webClient.getPage(url); // 解析获取页面
			HtmlForm form = page.getFormByName("form1"); // 得到搜索Form
			HtmlSubmitInput button = form.getInputByName("BTN_Submit"); // 获取提交按钮
			HtmlPage page2 = button.click(); // 模拟点击
			submit(page2.asXml());
			
			reAnswer(form);
			
//			autoExam(webClient);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			webClient.close(); // 关闭客户端，释放内存
		}
	}

	private void autoExam(WebClient webClient) throws Exception {
		String examUrl = "https://sqpx.91huayi.com/StudyCenter/examlilun.aspx?biaoshi=xd";
		HtmlPage page = webClient.getPage(examUrl);
		HtmlForm htmlForm = page.getFormByName("form1");
		return;
	}

	private void reAnswer(HtmlForm form) throws IOException {
		HtmlSubmitInput submitButton = form.getInputByName("BTN_Submit"); // 获取提交按钮
		for (int i = 0; i < answerList.size(); i++) {
			List<HtmlInput> htmlInputs = form.getInputsByValue("RBTN_Sign"+answerList.get(i));
			HtmlInput answerButton = htmlInputs.get(i);
			answerButton.click();
		}
		answerList.clear();
		submitButton.click();
	}

	public void submit(String html) throws IOException {
		Document document = Jsoup.parse(html);
		Elements elements = document.getElementsByClass("tooltip");
		try (FileOutputStream fileOutputStream = new FileOutputStream(new File("D://答案.doc"), true)) {

			for (Element element : elements) {
				String source = element.attr("title");
				String process = source.replaceAll("<.*?>", "").concat("\n");
				System.out.println(process);
				answerList.add(process.substring(process.length()-2, process.length()-1));
				fileOutputStream.write(process.getBytes());
			}
		}
	}
}

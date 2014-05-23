package ilearnrw.textadaptation;


public class Test 
{

	public static void main(String arg[])
	{
		TextAnnotationModule t = new TextAnnotationModule();
		t.setInputHTMLFile("C:\\Users\\Fouli\\Desktop\\Test.html");
		t.annotateText();
		
		
			//org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(input);
			
			/*int i = 0;

			
			java.util.Map<String, String> map = new java.util.HashMap<String, String>();
			map.put("color", "#00FF00");
			map.put("background", "red");*/
			
			/*for (org.jsoup.nodes.Element element : doc.select("w") )
			{
			       
				i++;
			        if (i == 1)
			        {
			        	t.setAttributesToWord(element, element.id(), map, 4, 5);
			        }
			        
			}*/
			       // i = 0;
			        
			        
			/*for (org.jsoup.nodes.Element element : doc.select("w") )
			{
				i++;
		        if (i == 1)
		        {
		        	t.removeAttributeFromWord(element,  element.id(), "color", 0, 9);
		        }

				System.out.println(element);
		       
			}*/
			        
			  
			
			        //System.out.print(element.text()+" ");
			        //element.attr("style", "color:pink;");
			        //element.appendElement("style");
			        //System.out.println(element.attr("style"));
			        //element.attr("style", element.attr("style") +" " +"background:yellow;");
			        //System.out.print(element.toString()+" "+element.id()+"\n");
			        //String innerInput = element.attr("style");
			        //System.out.println(innerInput);
			        
			        //element.removeAttr("style");
			        //java.util.StringTokenizer st = new java.util.StringTokenizer(innerInput, ";");
			        //StringBuffer attribute = new StringBuffer("");
			        //String token = "";
			        
			       /* if (st.countTokens()>2)
			        {
			        	while (st.hasMoreTokens()) 
				        {
				        	token = st.nextToken().trim();
				        	
				        	if (token.contains("color"))
				        	{
				        		//System.out.println(token);
				        	}
				        	else
				        	{
				        		attribute.append(token+";"); 
				        	}
				        	
				        }
				        element.attr("style", attribute.toString());*/
				        
				        
				        
			        //}
			        //element.appendElement("span");
			        
			        /*int start = 4;
			        int end = 5;
			        
			        String[] result = Test.setElementsText(element.text(), start, end);
			        
			        if (element.outerHtml().contains("span"))
			        {
			        	for (org.jsoup.nodes.Element e : element.select("span") )
						{
			        		String spanText = e.text();
			        		System.out.println(spanText);
			        		System.out.println(element.text().substring(start,end+1));
			        		
			        		if (spanText.equals(element.text().substring(start,end)))
			        		{
			        			e.attr("style", e.attr("style") +" " +"background:yellow;");
			        		}
			        		else
			        		{
			        			element.appendElement("span").attr("style","background:yellow;").text(result[1]);
			        		}
						}
			        }
			        else
			        {
			        	element.appendElement("span").attr("style","background:yellow;").text(result[1]);
			        }*/
			        	 
			        
			        
			       
			        
			        
			       // System.out.println(element.toString());
			        
			        
			        //t.setAttributesToWord(element, element.id(), map, 1, 3);
			        
			        //t.setAttributeToWord();
			        /*int start = 2;
			        int end = 3;
			        
			        String[] result = Test.setElementsText(element.text(), start, end);
			        
			        
			        element.empty();
			        element.append(result[0]+"<span style=\"color: #00FF00\">"+result[1]+"</span>"+result[2]);
			       
			        
			        //System.out.println(element.toString());
			}
			}*/
			
	}
}

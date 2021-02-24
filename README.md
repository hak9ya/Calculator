# 자바 계산기 프로그램
----------------------------

## 프로젝트 계획이유
> 유틸리티 프로그램을 구상하다가 가장 대중적인 계산 프로그램을 계획하였습니다.

## 프로젝트 정보(개발환경)
> 1. Eclipse2020-09 사용
> 2. JAVA swing, awt

## 프로젝트 주요 기능
![화면 캡처 2021-02-24 113146](https://user-images.githubusercontent.com/63631952/108938906-37e62880-7694-11eb-8fc6-c6fdcfd82653.png)

> 1. 사칙연산을 이용하여 계산이 가능함
> 2. 소수점을 이용하여 실수 표현이 가능함
> 백스페이스와 클리어 버튼을 이용하여 계산식을 지울 수 있음

- 전반적인 계산기능 로직

 ``` JAVA
	private void textSave(String inputText) {
		arrList.clear();
		
		for(int i = 0; i < inputText.length(); i++) {
			char ch = inputText.charAt(i);
			
			if(ch == '+' || ch == '-' || ch == '×' || ch == '÷') {
				arrList.add(value);
				value = "";
				arrList.add(ch + "");
			} else {
				value = value + ch;
			}
		}
		arrList.add(value);
		arrList.remove("");
	}
	
	public double calculate(String inputText) {
		textSave(inputText);
		
		double prevResult = 0;
		double nowValue = 0;
		String func = "";
		
		for(int i = 0; i < arrList.size(); i++) {
			String s = arrList.get(i);
			
			if(s.equals("+")) {
				func = "add";
			}else if(s.equals("-")) { 
				func = "sub";
			}else if(s.equals("×")) {
				func = "mul";
			}else if(s.equals("÷")) {
				func = "div";
			}else {
				if(func.equals("mul") || func.equals("div") && !s.equals("+") && !s.equals("-") && !s.equals("×") && !s.equals("÷")) {
					Double one = Double.parseDouble(arrList.get(i - 2));
					Double two = Double.parseDouble(arrList.get(i));
					Double result = 0.0;
					
					if(func.equals("mul")) {
						result = one * two;
					}else if(func.equals("div")) {
						result = one / two;
					}
					
					arrList.add(i+1, Double.toString(result));
					
					for(int j = 0; j < 3; j++) {
						arrList.remove(i - 2);
					}
					
					i -= 2;
				}
			}
		}
		
		for(String s : arrList) {
			if(s.equals("+")) {
				func = "add";
			} else if(s.equals("-")) {
				func = "sub";
			} else {
				nowValue = Double.parseDouble(s);	//double형 변환
				if(func.equals("add")) {
					prevResult += nowValue;
				} else if(func.equals("sub")) {
					prevResult -= nowValue;
				} else {
					prevResult = nowValue;
				}
			}
			prevResult = Math.round(prevResult * 100000) / 100000.0; 
		}
		return prevResult;
	}
```
- ArrayList를 이용하였고 메소드를 활용해서 값을 띄우게끔 하였다.

## 예외처리와 어려운 점
> 1. 계산기 프로그램이 간단해 보이지만 예외처리 할 부분이 생각보다 많았다.
> 2. 소수점이 두개 이상이면 안되고, 소수점과 사칙연산은 서로 붙을 수 없다.
> 3. 초기값에서 0이후에는 소수점 말고는 어떠한 정수가 붙을 수 없다.
> 4. 수정 중이다..

package edu.sfsu.cs.orange.ocr.ocr;

public class Filter {

	
	public char digitToAlphabet(char c){
		
		char a = c;
		switch(c){
		
		case '8' :
			a = 'B';
			break;
		case '0' :
			a = 'O';
			break;
		case '7' :
			a = 'T';
			break;
		case '5' :
			a = 'S';
			break;
		case '1' :
			a = 'I';
			break;
		case '3' :
			a = 'B';
			break;
		case '6' :
			a = 'G';
			break;
		case '4' :
			a = 'A';
			break;
		case '2' :
			a = 'Z';
			break;	
			
		
		}
		
		return a;
		
		
		
	}
	
public char AlphabetToDigit(char c){
		
		char a = c;
		switch(c){
		
		case 'B' :
			a = '8';
			break;
		case 'O' :
			a = '0';
			break;
		case 'T' :
			a = '7';
			break;
		case 'S' :
			a = '5';
			break;
		case 'I' :
			a = '1';
			break;
		
		case 'G' :
			a = '6';
			break;
			
		case 'D':
			a = '0';
			break;
		case 'Z':
			a = '2';
			break;
		case 'U':
			a = '0';
			break;
			
		
		}
		
		return a;
		
		
		
	}
	
}

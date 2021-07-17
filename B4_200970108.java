import java.util.*;
class A{
	int am;
	int year;
	int cbill;
	A(int am,int year,int cbill){
		this.am=am;
		this.year=year;
		this.cbill=cbill;
	}
}
abstract class Bank_Account{
	abstract void loanEligible();
}
class AccountNotExist extends Exception{	//User Defined Exception Class
	long no;
	AccountNotExist(long n){
		no=n;
	}
	public String toString(){	//Overriding the toString() method
		return "Account Number: "+no+" does not exist";
	}
}
class MyThread extends Bank_Account implements Runnable{
	int c[][];
	int a[][];
	int thno;
	int acno;
	static int acloc=0;
	static double emi=0;
	static double lpaycap=0;
	A ob;
	MyThread(int thno,int c[][],int a[][],int acno,A o){
		this.c=c;
		this.a=a;
		this.acno=acno;
		this.thno=thno;
		ob=o;
	}
	public void run(){
		if(thno==1){
			for(int i=0;i<c.length;i++){
				try{
					if(acno==c[i][0]){
						System.out.println("\nSearching Account Number And Cibil...");
						System.out.println("Acno"+acno+" Exists and Cbil Score is "+c[i][1]);
						acloc=i;
						break;
					}
					if(i==(c.length-1)){
						throw new  AccountNotExist(acno);
					}
				}catch(AccountNotExist e){
					System.out.println(e);
					System.exit(1);
				}
			}
		}
		if(thno==2){
			int in=inCal(c[acloc][1]);
			double tin=(ob.am*ob.year*in)/100;
			emi=(ob.am+tin)/(ob.year*12);
			System.out.print("\nEmi for New Loan: "+emi);
		}
		if(thno==3){
			lpaycap=a[acloc][1]/a[acloc][2];
			System.out.println("\nEMI pay Capacity: "+lpaycap);
		}
		if(thno==4){
			System.out.print("\nLoan Application Outcome: ");
			loanEligible();
		}
	}

	int inCal(int c){
		int temp=0;
		if(c<=25){
			temp=10;
		}else{
			if(c>=25 && c<=50){
				temp=9;
			}else{
				if(c>=51 && c<=75){
					temp=8;
				}else{
					if(c>=76){
						temp=7;
					}
				}
			}
		}
		return temp;
	}
	void loanEligible(){
		if(lpaycap>emi){
			System.out.println("Loan Possible "+lpaycap+" > "+emi);
		}else{
			System.out.println("Loan Not Possible "+lpaycap+" < "+emi);
		}
	}

}
class B4_200970108{
	public static void main(String ar[]){
		int cust_cibil[][]={
			{101,60},
			{102,89},
			{103,90}
			};
		int cust_past_loan[][]={
			{101,500000,48},
			{101,700000,60},
			{101,500000,66}
			};
		System.out.println("Bank Database\n");
		System.out.println("\nCBIL Scores");
		for(int i=0;i<cust_cibil.length;i++){
			for(int j=0;j<cust_cibil[i].length;j++){
				System.out.print(cust_cibil[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("\nPast Loan Histort");
		for(int i=0;i<cust_past_loan.length;i++){
			for(int j=0;j<cust_past_loan[i].length;j++){
				System.out.print(cust_past_loan[i][j]+"\t");
			}
			System.out.println();
		}
		Scanner sc=new Scanner(System.in);
		System.out.print("\nEnter The Account Number: ");
		int acno=sc.nextInt();
		System.out.print("Enter Loan Amount: ");
		int lamo=sc.nextInt();
		System.out.print("Enter Loan Duration in Year: ");
		int year=sc.nextInt();
		A ob=new A(lamo,year,0);
		MyThread m1=new MyThread(1,cust_cibil,cust_past_loan,acno,ob);
		Thread t1=new Thread(m1);
		t1.start();
		try{
			t1.join();
		}catch(Exception e){}
		System.out.println("\nFinding EMI for loan paid");
		MyThread m2=new MyThread(2,cust_cibil,cust_past_loan,acno,ob);
		Thread t2=new Thread(m2);
		t2.start();
		try{
			t2.join();
		}catch(Exception e){}
		MyThread m3=new MyThread(3,cust_cibil,cust_past_loan,acno,ob);
		Thread t3=new Thread(m3);
		t3.start();
		try{
			t3.join();
		}catch(Exception e){}
		MyThread m4=new MyThread(4,cust_cibil,cust_past_loan,acno,ob);
		Thread t4=new Thread(m4);
		t4.start();
	}
}

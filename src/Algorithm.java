
public class Algorithm {

	 public int[][] map = new int[20][20];   
	 private String black,white,winner,loser;
	 private int size=15;
	 private int color;
	 
	 public Algorithm(String player1,String player2,int color) {
		// TODO Auto-generated constructor stub
		 if(color==1)
		 {
			 this.black=player1;
			 this.white=player2;
		 }
		 else 
		 {
			 this.black=player2;
			 this.white=player1;
		 }	 
		 winner=loser="unkonwn";
		 this.color=color;
	}
	 
	 private boolean ck(int x,int y){
		 if(x>=0&&x<size&&y>=0&&y<size)
			 return true;
		 else return false;
	 }
	 
	 public boolean place(int x,int y){
		 if(!ck(x, y)||map[x][y]!=0)
			 return false;
		 map[x][y]=color;
		 if(end(x, y))
		 {
			 if(color==1)
			 {
				 winner=black+" ( black ) ";
				 loser=white+" ( white ) ";
			 }				
			 else{
				 winner=white+" ( white ) ";
				 loser=black+" ( black ) ";
			 }			 
		 }
		 return true;
	 }
	 
	 public String getWinner() {
		return winner;
	}
	 
	 public String getLoser() {
		return loser;
	}
	 
	 public void setWinner(String winner) {
		this.winner = winner;
	}
	 
	 public void setLoser(String loser) {
		this.loser = loser;
	}
	 
	 private boolean end(int x,int y){
		 int ct=1,xx,yy;
		 for(xx=x-1;xx>=0;xx--){
			 if(map[xx][y]!=color)
				 break;
			 else ct++;
		 }
		 for(xx=x+1;xx<size;xx++){
			 if(map[xx][y]!=color)
				 break;
			 else ct++;
		 }
		 if(ct>=5) return true;
		 else ct=1;
		 for(yy=y-1;yy>=0;yy--){
			 if(map[x][yy]!=color)
				 break;
			 else ct++;
		 }
		 for(yy=y+1;yy<size;yy++){
			 if(map[x][yy]!=color)
				 break;
			 else ct++;
		 }
		 if(ct>=5) return true;
		 else ct=1;
		 for(xx=x-1,yy=y-1;xx>=0&&yy>=0;xx--,yy--){
			 if(map[xx][yy]!=color)
				 break;
			 else ct++;
		 }
		 for(xx=x+1,yy=y+1;xx<size&&yy<size;xx++,yy++){
			 if(map[xx][yy]!=color)
				 break;
			 else ct++;
		 }
		 if(ct>=5) return true;
		 else ct=1;
		 for(xx=x-1,yy=y+1;xx>=0&&yy<size;xx--,yy++){
			 if(map[xx][yy]!=color)
				 break;
			 else ct++;
		 }
		 for(xx=x+1,yy=y-1;xx<size&&yy>=0;xx++,yy--){
			 if(map[xx][yy]!=color)
				 break;
			 else ct++;
		 }
		 if(ct>=5) return true;
		 else return false;
	 }
}

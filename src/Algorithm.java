
public class Algorithm {

	 public int[][] map = new int[20][20];   
	 private String player;
	 private int size=15;
	 private int color;
	 private boolean win=false,end=false;
	 
	 public Algorithm(String player,int color) {
		// TODO Auto-generated constructor stub
		 if(color==1)
		 {
			 this.player=player;
		 }
		 else 
		 {
			 this.player=player;
		 }	 
		 this.color=color;
		 win=false;
		 end=false;
	}
	 
	 private boolean ck(int x,int y){
		 if(x>=0&&x<size&&y>=0&&y<size)
			 return true;
		 else return false;
	 }
	 
	 public boolean place(int x,int y,int color){		 
		 if(!ck(x, y)||map[x][y]!=0)
			 return false;
		 map[x][y]=color;
		 if(color==this.color&&end(x, y))
		 {
			  setEnd(true);
			  setWin(true);
		 }
		 return true;
	 }
	 
     public void setWin(boolean win) {
		this.win = win;
	}
     
     public boolean isWin() {
		return win;
	}
     
     public void setEnd(boolean end) {
		this.end = end;
	}
     
     public boolean isEnd() {
		return end;
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

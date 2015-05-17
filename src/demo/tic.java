package demo;

public class tic {

	private int X = 0, O = 0;
	private void put(int pos) {
		X = X | pos & -((pos >> 31) + 1) & ~O;
		O = O | -pos & (pos >> 31) & ~X;
	}
	private void clear(int pos) {
		X = X & ~pos;
		O = O & ~pos;
	}
	private boolean Check(int P) {
		return  (P & 0x007) == 7 || 
				(P & 0x038) == 0x038 || 
				(P & 0x1C0) == 0x1C0 || 
				(P & 0x049) == 0x49 || 
				(P & 0x092) == 0x92 || 
				(P & 0x124) == 0x124 || 
				(P & 0x111) == 0x111 ||
				(P & 0x054) == 0x54;
	}
	private int GameOver() {
		return Check(X) ? 2048 : Check(O) ? 512 : ((X | O) & 511) == 511 ? 1024 : 0;
	}
	private int NegaMax(int p) {
		int End = GameOver();
		if (End != 0)
			return End;

		int best_value = (p == 1) ? 512 : 2048;
		for (int b = 1; b <= 256; b = b << 1) {
			int move = (~(X | O) & b);
			if (move != 0) {
				put(p * move);
				int s = NegaMax(-p);
				best_value = p * (s & 0xfffffe00) > p
						* (best_value & 0xfffffe00) ? ((s & 0xfffffe00) | move)
						: best_value;
				clear(move);
			}

		}
		return best_value;
	}
	private int PosToBit(int pos) {
		return pos>=1 && pos<=9 ? 1<<(pos-1) : 0;
	}

	private int BitToPos(int bitNum) {
		int result = 1;
		while ( (bitNum=bitNum>>1) > 0 ) result++;   
        return result;
	}
	public boolean Move(int pos, int player) {
		int p = PosToBit(pos);
		if ( p!=0  && ( player==1 || player==-1 ) && ((X | O) & p)==0) {
		  put(p*player);
		  return true;
		} else
		  return false; 
	}
	public int GenerateMove(int Player) {
		return BitToPos((NegaMax(Player) & 511));
	}

	
// -==-=-=-=- getX -==-=-=-=-=-=-=-=-=-=-=- 	
	public int getX() {
		return X;
	}

// -==-=-=-=- getO -==-=-=-=-=-=-=-=-=-=-=-	
	public int getO() {
		return O;
	}
	
// -==-=-=-=- getBoard -==-=-=-=-=-=-=-=-=-=-=-
	public int getBoard() {
		return (X|O);
	}
	
// -==-=-=-=- ClearBoard -==-=-=-=-=-=-=-=-=-=-=-	
	public void NewGame() {
		X=O=0;
	}
	
	/* 1 - X player won
	  -1 - O player won
	   2 - drawn
	   0 - game still goes on
	*/
// -==-=-=-=-= isGameOver =-=-=-=-=-=-=-=-=-=-=-=-=-
	public int isGameOver() {
		return Check(X) ? 1 : Check(O) ? -1 : ((X | O) & 511) == 511 ? 2 : 0;
	}
}

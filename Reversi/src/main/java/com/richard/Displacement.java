package com.richard;

public interface Displacement {
	public int next(int originpos);

	public static int MINPOS = 0;
	public static int MAXPOS = 63;
}

abstract class HorizontalDisplacement implements Displacement {
	@Override
	public int next(int i) {
		if (i >= MINPOS && i <= MAXPOS) {
			return actualNext(i);
		} else {
			throw new IllegalArgumentException("not allowed");
		}
	}

	public abstract int actualNext(int i);
}

class HorizontalBackwardDisplacement extends HorizontalDisplacement {
	public int actualNext(int i) {
		if (i % 8 == 0) {
			throw new IllegalArgumentException("left edge");
		} else {
			return i - 1;
		}
	}
}

class HorizontalForwardDisplacement extends HorizontalDisplacement {
	public int actualNext(int i) {
		if (i % 8 == 7) {
			throw new IllegalArgumentException("right edge");
		} else {
			return i + 1;
		}
	}
}

abstract class VerticalDisplacement implements Displacement {
	@Override
	public int next(int i) {
		if (i >= getValidMin() && i <= getValidMax()) {
			return actualNext(i);
		} else {
			throw new IllegalArgumentException("not allowed");
		}
	}
	
	public abstract int getValidMin();

	public abstract int getValidMax();
	
	public abstract int actualNext(int i);
	
}

class VerticalBackwardDisplacement extends VerticalDisplacement {

	@Override
	public int getValidMin() {
		return MINPOS + 8;
	}

	@Override
	public int getValidMax() {
		return MAXPOS;
	}

	@Override
	public int actualNext(int i) {
		return i - 8;
	}
	
}

class VerticalForwardDisplacement extends VerticalDisplacement {
	@Override
	public int getValidMin() {
		return MINPOS;
	}

	@Override
	public int getValidMax() {
		return MAXPOS - 8;
	}

	@Override
	public int actualNext(int i) {
		return i + 8;
	}
}

abstract class ChainDisplacement implements Displacement {
	Displacement first = null;
	Displacement second = null;
	public ChainDisplacement(Displacement first, Displacement second) {
		if (first == null || second == null) {
			throw new IllegalArgumentException("not properly defined!");
		}
		this.first = first;
		this.second = second;
	}
	
	public int next(int i) {
		return second.next(first.next(i));
	}
}

class DiagonalRight2LeftBackwardDisplacement extends ChainDisplacement{
	public DiagonalRight2LeftBackwardDisplacement() {
		super(new VerticalBackwardDisplacement()
			, new HorizontalForwardDisplacement());
	}
}

class DiagonalRight2LeftForwardDisplacement extends ChainDisplacement{
	public DiagonalRight2LeftForwardDisplacement() {
		super(new VerticalForwardDisplacement()
			, new HorizontalBackwardDisplacement());
	}
}

class DiagonalLeft2RightBackwardDisplacement extends ChainDisplacement {
	public DiagonalLeft2RightBackwardDisplacement() {
		super(new VerticalBackwardDisplacement()
			, new HorizontalBackwardDisplacement());
	}
}

class DiagonalLeft2RightForwardDisplacement extends ChainDisplacement {
	public DiagonalLeft2RightForwardDisplacement() {
		super(new VerticalForwardDisplacement()
			, new HorizontalForwardDisplacement());
	}
}

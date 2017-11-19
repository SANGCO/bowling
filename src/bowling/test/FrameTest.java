package bowling.test;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bowling.Frame;

class FrameTest {

	private Frame frame;
	
	@BeforeEach
	public void setUp() {
		frame = new Frame();
	}

	@Test
	public void methodRollTest() {
		//Frame frame = new Frame();
		frame.roll(10);
		int result = frame.rolls.get(0);
		assertEquals(10, result);
	}
}

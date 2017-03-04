import org.junit.Test;

public class InnerClassTest {

	@Test
	public void testInner () {
		InnerTest innerTest1 = new InnerTest(0);
		InnerTest innerTest2 = new InnerTest(0);
		
		innerTest2.indexAdd();
		
		for (int i = 0; i < 20; i++) {
			innerTest1.indexAdd();
			innerTest1.printIndex();
			innerTest2.printIndex();
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class InnerTest {
		
		private int index;

		public InnerTest(int index) {
			super();
			this.index = index;
		}

		public void indexAdd() {
			index ++;
		}
		
		public void printIndex () {
			System.out.println(index);
		}
	}
}

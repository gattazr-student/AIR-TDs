package jus.aor.nio.v3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;

/**
 * @author morat
 */
public class WriteCont extends Continuation {
	// state automata
	private enum State {
		WRITING_DATA, WRITING_DONE, WRITING_LENGTH;
	}

	// buf contains the byte array that is currently written
	protected ByteBuffer buf = null;
	private SelectionKey key;
	// the list of bytes messages to write
	protected ArrayList<byte[]> msgs = new ArrayList<>();
	// initial state
	protected State state = State.WRITING_DONE;

	/**
	 * @param k
	 * @param sc
	 */
	public WriteCont(SelectionKey k, SocketChannel sc) {
		super(sc);
		key = k;
	}

	/**
	 * @throws IOException
	 */
	protected void handleWrite() throws IOException {
		if (state == State.WRITING_DONE) {
			if (msgs.size() > 0) {
				buf = intToBytes(msgs.get(0).length);
				state = State.WRITING_LENGTH;
			}
		}
		if (state == State.WRITING_LENGTH) {
			socketChannel.write(buf);
			if (buf.remaining() == 0) {
				buf = ByteBuffer.wrap(msgs.get(0));
				state = State.WRITING_DATA;
			}
		}
		if (state == State.WRITING_DATA) {
			socketChannel.write(buf);
			if (buf.remaining() == 0) {
				msgs.remove(0);
				state = State.WRITING_DONE;
				if (msgs.isEmpty()) {
					// do not keep OP-WRITE needlessly
					key.interestOps(SelectionKey.OP_READ);
				}
			}
		}
	}

	/**
	 * @return true if the msgs are not completly write.
	 */
	protected boolean isPendingMsg() {
		return (msgs.size() > 0);
	}

	/**
	 * @param data
	 * @throws IOException
	 */
	protected void sendMsg(Message data) throws IOException {
		msgs.add(data.marshall());
		if (!(key.isWritable())) {
			key.interestOps(SelectionKey.OP_WRITE);
		}
	}
}

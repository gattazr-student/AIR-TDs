package jus.aor.nio.v3;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author morat
 */
public class ReadCont extends Continuation {
	// state automata
	private enum State {
		READING_DATA, READING_LENGTH;
	}

	// buffer for reading the length of a message
	protected ByteBuffer buf = ByteBuffer.allocate(4);
	// the length of the message to read
	protected int length;
	private int pConmpteur;

	// current state
	protected State state = State.READING_LENGTH;

	/**
	 * @param sc
	 */
	public ReadCont(SocketChannel sc) {
		super(sc);
	}

	/**
	 * @return the message
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	protected Message handleRead() throws IOException, ClassNotFoundException {
		int nbRead = 0;
		if (state == State.READING_LENGTH) {
			nbRead = socketChannel.read(buf);
			if (nbRead == -1) {
				// The channel has been closed (eof)
				throw new IOException("nio channel closed");
			}
			if (buf.remaining() == 0) {
				// we have read the four bytes containing the length
				length = bytesToInt(buf);
				// by allocating a buffer with the expected length, we ensure
				// that
				// the buffer will not contain extra bytes belonging to the next
				// message to read
				buf = ByteBuffer.allocate(length);
				pConmpteur = 0;
				state = State.READING_DATA;
			}
		}
		if (state == State.READING_DATA) {
			nbRead = socketChannel.read(buf);
			pConmpteur++;
			if (nbRead == -1) {
				// The channel has been closed (eof)
				throw new IOException("nio channel closed");
			}
			if (buf.remaining() == 0) {
				// the full message has been received
				byte[] data = buf.array();
				buf = ByteBuffer.allocate(4);
				state = State.READING_LENGTH;
				return new Message(data, pConmpteur);

			}
		}
		return null;
	}
}

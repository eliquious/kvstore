/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.kvstore.holders;
import java.nio.ByteBuffer;

/**
 * Holder for Null value
 *
 * @author Guillermo Grandes / guillermo.grandes[at]gmail.com
 */
public final class NullHolder extends DataHolder<NullHolder> {
	public static final NullHolder NULL = new NullHolder();
	//
	public static NullHolder valueOf() {
		return NULL;
	}

	/**
	 * Constructor necesario para la deserializacion
	 */
	public NullHolder() {};

	// ========= Basic Object methods =========

	public String toString() {
		return "null";
	}

	public int hashCode() {
		return 0;
	}

	// ========= Comparable =========

	public boolean equals(final Object obj) {
		if (obj instanceof NullHolder) {
			return true;
		}
		return false;
	}

	public int compareTo(final NullHolder another) {
		return 0;
	}

	// ========= Serialization =========

	public final int byteLength() {
		return 0;
	}

	public void serialize(ByteBuffer buf) {
	}
	public NullHolder deserialize(ByteBuffer buf) {
		return valueOf();
	}

}


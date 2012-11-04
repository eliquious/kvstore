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
package org.infra.structures.btree;

import org.infra.holders.DataHolder;
import org.infra.structures.hash.IntHashMap;

/**
 * Implementation of B+Tree in Memory
 * This class is Thread-Safe
 *
 * @author Guillermo Grandes / guillermo.grandes[at]gmail.com
 */
public final class BplusTreeMemory<K extends DataHolder<K>, V extends DataHolder<V>> extends BplusTree<K, V> {

	/**
	 * Storage of nodes 
	 */
	@SuppressWarnings("rawtypes")
	private final IntHashMap<Node> storeNodes = new IntHashMap<Node>(17, Node.class);

	/**
	 * Current nodeid from underlying storage
	 */
	private int maxNodeID = 0;

	/**
	 * Create B+Tree in Memory
	 * 
	 * @param autoTune if true the tree try to find best b-order for leaf/internal nodes to fit in a block of b_size bytes
	 * @param b_size if autoTune is true is the blockSize, if false is the b-order for leaf/internal nodes
	 * @param typeK the class type of Keys
	 * @param typeV the class type of Values
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public BplusTreeMemory(final boolean autoTune, final int b_size, final Class<K> typeK, final Class<V> typeV) throws InstantiationException, IllegalAccessException {
		super(autoTune, b_size, typeK, typeV);
		clearStates();
		System.out.println("BplusTreeMemory.hashCode()=" + this.hashCode());
	}

	@Override
	public int getHighestNodeId() {
		return maxNodeID;
	}

	@Override
	protected int allocNode(final boolean isLeaf) {
		final int id = ++maxNodeID;
		return (isLeaf ? id : -id);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Node<K, V> getNode(final int nodeid) {
		return storeNodes.get(nodeid);
	}

	@Override
	protected void putNode(final Node<K, V> node) {
		storeNodes.put(node.id, node);
	}

	@Override
	protected void freeNode(final Node<K, V> node) {
		final int nodeid = node.id;
		if (nodeid == Node.NULL_ID) {
			System.out.println(this.getClass().getName() + "::freeNode(" + nodeid + ") ERROR");
			return;
		}
		node.clear();
		storeNodes.remove(nodeid);
	}

	@Override
	protected void releaseNodes() {
		// Nothing
	}

	@Override
	protected boolean clearStorage() {
		storeNodes.clear();
		return true;
	}

	@Override
	protected void clearStates() {
		maxNodeID = 0;
		//
		// Reset Root node
		super.clearStates();
		// Sync changes
		validState = true;
	}

}
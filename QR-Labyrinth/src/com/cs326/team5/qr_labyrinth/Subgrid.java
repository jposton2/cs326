package com.cs326.team5.qr_labyrinth;

import java.util.ArrayList;



public class Subgrid {
		
		ArrayList<PointData> subgridArray = new ArrayList<PointData>();
		private int incomingSubgridIndex=-1, outgoingNodeIndex=-1, dummyNodeIndex  =-1;
		
		int groupID;
		private Point teleporterOne = new Point();//incoming teleporter
		private Point teleporterTwo = new Point();//outgoing teleporter
		private Point teleporterThree = new Point();//dummy teleporter
		
		/**
		 * Set teleporter one coordinates
		 */
		void setTeleporterOne(int x, int y){
			teleporterOne.setX(x);
			teleporterOne.setY(y);;
		}
		
		/**
		 * Set teleporter two coordinates
		 */
		void setTeleporterTwo(int x, int y){
			teleporterTwo.setX(x);
			teleporterTwo.setY(y);;
		} 
		
		Point getTeleporterOne(){
			return teleporterOne;
		}
		
		Point getTeleporterTwo(){
			return teleporterTwo;
		}
		
		/**
		 * @return the incomingNodeIndex
		 */
		public int getIncomingNodeIndex() {
			return incomingSubgridIndex;
		}

		/**
		 * @param incomingSubgridIndex the incomingNodeIndex to set
		 */
		public void setIncomingSubgridIndex(int incomingSubgridIndex) {
			this.incomingSubgridIndex = incomingSubgridIndex;
		}

		/**
		 * @return the outgoingNodeIndex
		 */
		public int getOutgoingGridIndex() {
			return outgoingNodeIndex;
		}

		/**
		 * @param outgoingNodeIndex the outgoingNodeIndex to set
		 */
		public void setOutgoingGridIndex(int outgoingNodeIndex) {
			this.outgoingNodeIndex = outgoingNodeIndex;
		}
		
		/**
		 * @return the dummyNodeIndex
		 */
		public int getDummyNodeIndex() {
			return dummyNodeIndex;
		}

		/**
		 * @param dummyNodeIndex the dummyNodeIndex to set
		 */
		public void setDummyNodeIndex(int dummyNodeIndex) {
			this.dummyNodeIndex = dummyNodeIndex;
		}
		
		/**
		 * @return the teleporterThree
		 */
		public Point getTeleporterThree() {
			return teleporterThree;
		}

		/**
		 * @param teleporterThree the teleporterThree to set
		 */
		public void setTeleporterThree(int x, int y) {
			teleporterThree.setX(x);
			teleporterThree.setY(y);
		}
		
}

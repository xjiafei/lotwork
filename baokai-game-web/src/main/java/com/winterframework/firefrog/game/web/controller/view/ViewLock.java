package com.winterframework.firefrog.game.web.controller.view;

import java.util.ArrayList;
import java.util.List;

import com.winterframework.firefrog.game.web.dto.BlockadeStrutResponseDTO;
import com.winterframework.firefrog.game.web.dto.PointsStrucRequestDTO;

public class ViewLock {
		private List<BlockadeStrutResponseDTO> blockadeList = new ArrayList<BlockadeStrutResponseDTO>();
		private List<PointsStrucRequestDTO> pointsList = new ArrayList<PointsStrucRequestDTO>();

		public List<BlockadeStrutResponseDTO> getBlockadeList() {
			return blockadeList;
		}

		public void setBlockadeList(List<BlockadeStrutResponseDTO> blockadeList) {
			this.blockadeList = blockadeList;
		}

		public List<PointsStrucRequestDTO> getPointsList() {
			return pointsList;
		}

		public void setPointsList(List<PointsStrucRequestDTO> pointsList) {
			this.pointsList = pointsList;
		}

	}
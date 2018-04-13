package com.winterframework.firefrog.game.web.dto;

/** 
* @ClassName: BlockadeStrutResponseDTO 
* @Description: 用于显示详细封锁变价信息的结构体
* @author 你的名字 
* @date 2014-5-30 上午10:10:17 
*  
*/
public class BlockadeStrutResponseDTO extends BlockadeResponseDTO {
	/** 
	* 投注内容
	*/
	private String betType;

	public BlockadeStrutResponseDTO(BlockadeResponseDTO blockadeResponseDTO, String betTypeName) {
		this.betType = betTypeName;
		this.beishu = blockadeResponseDTO.getBeishu();
		this.blockadeDetail = blockadeResponseDTO.getBlockadeDetail();
		this.realBeishu = blockadeResponseDTO.getRealBeishu();
	}

	public BlockadeStrutResponseDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getBetType() {
		return betType;
	}

	public void setBetType(String betType) {
		this.betType = betType;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BlockadeStrutResponseDTO)) {
			return false;
		}
		BlockadeStrutResponseDTO dto = (BlockadeStrutResponseDTO) obj;
		if (!dto.getBetType().equals(this.getBetType())) {
			return false;
		}
		if (dto.getBeishu().longValue() != this.getBeishu().longValue()) {
			return false;
		}
		if (dto.getRealBeishu().longValue() != this.getRealBeishu().longValue()) {
			return false;
		}
		return true;
	}
}

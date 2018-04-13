<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');

class agent extends CI_Controller {
	
	function downloadMultiply()
	{
		$filename = "downloadMultiply_" . date("YmdHi") . ".xls";
				
		//header("Content-type: text/csv");
		header("Content-Type: application/vnd.ms-excel; charset=utf-8");
		header("Content-Disposition: attachment; filename=" . $filename);
		header("Pragma: no-cache");
		header("Expires: 0");
		
		$this->load->model('App_multiply_model');
		$rs = $this->App_multiply_model->getAppMultiplyActivity();
		
		echo "\xEF\xBB\xBF";
		echo '<div><table id="tblDetail" class="tblShow" border="1" cellspacing="0" cellpadding="4"><tbody>'
			. '<tr>'
			. '<td>app_code</td>'
			. '<td>开户链结</td>'
			. '<td>图片</td>'
			. '</tr>';
			
		foreach($rs as $key => $value)
		{
			echo '<tr>';
				echo '<td>' . $value["app_code"]  . '</td>';
				echo '<td>' . $value["link"]  . '</td>';
				echo '<td>' . $value["image"]  . '</td>';
			echo '</tr>';
		}
		echo '</tbody></table></div>';
	}
	
	function downloadLog()
	{
		$filename = "downloadLog_" . date("YmdHi") . ".xls";
				
		//header("Content-type: text/csv");
		header("Content-Type: application/vnd.ms-excel; charset=utf-8");
		header("Content-Disposition: attachment; filename=" . $filename);
		header("Pragma: no-cache");
		header("Expires: 0");
		
		$this->load->model('App_multiply_log_model');
		$rs = $this->App_multiply_log_model->getData();
		
		echo "\xEF\xBB\xBF";
		echo '<div><table id="tblDetail" class="tblShow" border="1" cellspacing="0" cellpadding="4"><tbody>'
			. '<tr>'
			. '<td>app_code</td>'
			. '<td>uuid</td>'
			. '<td>第一次打开时间</td>'
			. '</tr>';
			
		foreach($rs as $key => $value)
		{
			echo '<tr>';
				echo '<td>' . $value["app_code"]  . '</td>';
				echo '<td>' . $value["uuid"]  . '</td>';
				echo '<td>' . $value["create_time"]  . '</td>';
			echo '</tr>';
		}
		echo '</tbody></table></div>';
	}
	
	function downloadLogin()
	{
		$filename = "downloadLogin_" . date("YmdHi") . ".xls";
				
		//header("Content-type: text/csv");
		header("Content-Type: application/vnd.ms-excel; charset=utf-8");
		header("Content-Disposition: attachment; filename=" . $filename);
		header("Pragma: no-cache");
		header("Expires: 0");
		
		$this->load->model('App_multiply_login_model');
		$rs = $this->App_multiply_login_model->getData();
		
		echo "\xEF\xBB\xBF";
		echo '<div><table id="tblDetail" class="tblShow" border="1" cellspacing="0" cellpadding="4"><tbody>'
			. '<tr>'
			. '<td>app_code</td>'
			. '<td>uuid</td>'
			. '<td>帐户名</td>'
			. '<td>最后登入时间</td>'
			. '<td>总登入次数</td>'
			. '</tr>';
			
		foreach($rs as $key => $value)
		{
			echo '<tr>';
				echo '<td>' . $value["app_code"]  . '</td>';
				echo '<td>' . $value["uuid"]  . '</td>';
				echo '<td>' . $value["username"]  . '</td>';
				echo '<td>' . $value["last_login_time"]  . '</td>';
				echo '<td>' . $value["counter"]  . '</td>';
			echo '</tr>';
		}
		echo '</tbody></table></div>';
	}
}
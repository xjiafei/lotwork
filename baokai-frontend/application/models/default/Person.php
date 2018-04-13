<?php	

class Head {
	
	public $sowner = 1;
	public $rowner  = 1;
	public $msn = 9999999;
	public $msnsn =  0;
	public $sendtime = 0;
	public $userId = 34 ;
	public $userAccount = '' ;
	public function __construct(){
		$header = new Header();
		$data   = $header->getDefaultMap();
		$this->sowner 	= $data['sowner'] ;
		$this->rowner 	= $data['rowner'] ;
		$this->msn 		= $data['msn'] ;
		$this->msnsn 	= $data['msnsn'] ;
		$this->sendtime = $data['sendtime'] ;
		if ($data['userId']) {
			$this->userId 	= $data['userId'];
		}
		if($data['userAccount']){
			$this->userAccount = $data['userAccount'] ;
		}
		unset($header);
		unset($data);
	}
}

class Pager {
	public $startNo = 1  ;
	public $endNo = 3 ;
	public function __construct($startNO = 1 , $endNo = 3,$total=null){
			$this->startNo = $startNO ;
			$this->endNo = $endNo ;
			if($total){
				$this->total = $total ;
			}
	}
	
}

class Body {
	public $pager = null ;
	public function add($pager , $result = null){
		$this->pager = $pager ;
		if($result){
			$this->result = $result ;
		}
	}
	
}

class BRequest  {
	public $head = null; 
	public $body = null ;
	
	public function add($head ,$body){
		$this->head = $head ;
		$this->body = $body ;
	}
}

////////////////////////////////////////////////////////////////////////////
class Sms  {
	public $id=0;
	public $sender = 1;
	public $sendTime = 1;
	public $receiveTime = 1;
	public $isRead = 1;
	public $parentId = 0;
	public $title = "" ;
	public $content  = "" ;
	public $type = 2;
	public $sendAccount  = "";
	public $receiveAccount = "";
	public $owner = "";
	public $msgType = 1;
	public $sendMsgRout = "";
	public $sendFrom = 0;
	public $receiveFrom = 0;
	public $gmtModified = 0;
	public function __construct($id = 0,$sender = 1 ,$sendTime = 1,$receiveTime = 1,
			$isRead = 1,$parentId = 0,$title = "",$content = "",$type = 2,$sendAccount = "",
			$receiveAccount = "" ,$owner = "",$msgType = 1 ,$sendMsgRout = "",$sendFrom = 0,
			$receiveFrom = 0 ,$gmtModified = 0){
		$this->id = $id;
		$this->sender = $sender;
		$this->sendTime = $sendTime;
		$this->receiveTime = $receiveTime;
		$this->isRead = $isRead;
		$this->parentId = $parentId;
		$this->title = $title;
		$this->content = $content;
		$this->type = $type;
		$this->sendAccount = $sendAccount;
		$this->receiveAccount = $receiveAccount;
		$this->owner = $owner;
		$this->msgType = $msgType;
		$this->sendMsgRout = $sendMsgRout;
		$this->sendFrom = $sendFrom;
		$this->receiveFrom = $receiveFrom ;
		$this->gmtModified = $gmtModified ;
	}
}

class Result{
	public $unreadCnt = 11;
	public $receives = array() ;
	
	public function __construct(){		
	}
	public function add($Receives ,$unreadCnt){
		$this->receives = $Receives ;
		$this->unreadCnt = $unreadCnt;
		
	}
}


class BResponse  {
	public $head = null;
	public $body = null ;
	
	public function __construct(){
		
	}
	
	public function add($head ,$body){
		$this->head = $head ;
		$this->body = $body ;
	}
}




class Pagers extends BaseModel {
	
	public $startNo = 010;
	public $endNo = 2;
	public $total = 122;
	public $test = "tset";
	
	protected function getTestData(){
		return "application/testdata/Pagers.php" ;
	}
	protected function getDefaultMap(){
		return array(
				'startNo' =>010,
				'endNo'=> 2,
				'total' => 122,
				'test' => "tset",
		);
	}
}

class Smses extends BaseModel {
/* 	public $id = 0;
	public $sender = 11;
	public $sendTime = 12;
	public $sendTimsse = "tset"; */
	
	protected function getTestData(){
		return "application/testdata/Pagers.php" ;
	}
	
	public function getDefaultMap(){
		
		return array(
				'id' =>0,
				'sender'=> 1,
				'sendTime' => 1,
				'sendTimsse' => "defaultvalue",
		);
		
	}
}

class Person extends baseModelTest implements fireFogSystemFace { 
		public $BRequest = null;
		public $bodyResponse = array(
							"id"=>11,
							"sender"=>11,
							"sendTime"=>11,
							"receiveTime"=>11,
							"isRead"=>11,
							"parentId"=>0,
							"parentIds"=>0,
							"title"=>"",
							"content" => "" ,
							"parentcontent" => "" ,
							"parentcontentsss" => "" ,
							"type" => 2,
							"sendAccount" =>"",
							"receiveAccount" =>"",
							"owner" =>"",
							"msgType"=>1,
							"sendMsgRout"=>"",
							"sendFrom"=>0,
							"receiveFrom"=>0,
							"gmtModified"=>0,
							"unreadCnt" =>11,
							"startNo"=>11,
							"endNo"=>11,
							"total"=>21
				);
		
/* 		public function __construct(){
			$this->BRequest = new BodyRequest();
		} */
		public function set($BRequest){
			$this->BRequest = $BRequest ;
		}
		
		public  function get(){		
/*  			$UserIns = new Pagers();
 			$tasks = $UserIns->getMember("result") ;
 			foreach($tasks as $k1 => $v1){
 				var_dump(json_encode($v1)) ;
 				$smses =  new Smses($v1);
 				var_dump(json_encode($smses->getMember("sender"))) ;
 			}
 			die; */
 			
/*  			$smsesa =  new Smses();
 			$smsesa->setMember("id",101);
 			$smsesa->setMember("sender",102);
 			$smsesa->setMember("sendTime",103);
 			$smsesa->setMember("sendTimsse",104);
 			
 			$smsesa->save();
 			die;
			 */
/* 			$tBResponse = new BResponse();
			$tBody =  new Body();
			$tResult = new Result() ;
			$Pager =  new Pager(1,3,20);
			$tSmsArray  = array() ; 
			array_push($tSmsArray,new Sms());
			$tResult->add($tSmsArray,20 ) ;
			$tBody->add($Pager,$tResult) ;
			$tBResponse->add(null, $tBody);
			var_dump(json_encode($tBResponse));die; */
			
			return parent::load();
		}
}

<?php
class Encrypt{
    // 公钥
    protected $key = 'lee'; 
    private function keyED($txt,$encrypt_key){
        $encrypt_key = md5($encrypt_key);
        $ctr=0;
        $tmp = '';
        for ($i=0;$i<strlen($txt);$i++){
            if ($ctr==strlen($encrypt_key)){
                $ctr=0;
            }
            $tmp.= substr($txt,$i,1) ^ substr($encrypt_key,$ctr,1);
            $ctr++;
        }
        return $tmp;
    }
 
    public function encrypt($txt,$key=''){
        if(empty($key)){
            $key=$this->key;
        }
        srand((double)microtime()*1000000);
        $encrypt_key = md5(rand(0,32000));
        $ctr=0;
        $tmp = '';
        for ($i=0;$i<strlen($txt);$i++)  {
            if ($ctr==strlen($encrypt_key)){
                $ctr=0;
            }
            $tmp.= substr($encrypt_key,$ctr,1).(substr($txt,$i,1) ^ substr($encrypt_key,$ctr,1));
            $ctr++;
        }
        return $this->keyED($tmp,$key);
    }
 
    public function decrypt($txt,$key=''){
        if(empty($key)){
            $key=$this->key;
        }
 
        $txt = $this->keyED($txt,$key);
        $tmp = '';
        for ($i=0;$i<strlen($txt);$i++){
            $md5 = substr($txt,$i,1);
            $i++;
            $tmp.= (substr($txt,$i,1) ^ $md5);
        }
        return $tmp;
    }
 
    public function setKey($key){
        if(empty($key)){
            return null;
        }
        $this->key=$key;
    }
 
    public function getPK(){
        return $this->key;
    }
 
}
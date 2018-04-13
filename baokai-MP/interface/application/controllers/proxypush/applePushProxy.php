<?php
    $request = json_decode(file_get_contents('php://input'), true);
    //var_dump(file_get_contents('php://input'));
    //echo '------';
    //var_dump($request);
    //echo '------';
    //var_dump($request['message']);
    //echo '------';
    //var_dump($request['deviceID']);
    //echo '------';
    if(!$request)
    {
        echo '$request null';
        die;
    }
    $pushkeyfile = './'.($request['pem_file'] ? $request['pem_file'] : 'applepush_caipiao.pem');
    $url = "ssl://gateway.push.apple.com:2195";
    $ctx = stream_context_create();
    $passphrase = "123qwe";
    stream_context_set_option($ctx, 'ssl', 'local_cert', $pushkeyfile);
    stream_context_set_option($ctx, 'ssl', 'passphrase', $passphrase);
    $fp = stream_socket_client($url
                            , $err
                            , $errstr
                            , 60
                            , STREAM_CLIENT_CONNECT|STREAM_CLIENT_PERSISTENT
                            , $ctx);
    if (!$fp)
    {
        echo -1;
        die;
    }
    $body['aps'] = array('alert' => $request['message'], 'sound' => 'default');
    $payload = json_encode($body);
    $msg = chr(0).pack('n', 32).pack('H*', $request['deviceID']).pack('n', strlen($payload)).$payload;
    $result = fwrite($fp, $msg, strlen($msg));
    fclose($fp);
    echo $result;
?>
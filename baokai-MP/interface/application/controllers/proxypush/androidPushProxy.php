<?php
    $request = json_decode(file_get_contents('php://input'), true);
    //var_dump(file_get_contents('php://input'));
    //echo '------';
    //var_dump($request);
    //echo '------';
    //var_dump($request['message']);
    //echo '------';
    //var_dump($request['deviceIDs']);
    //echo '------';
    if(!$request)
    {
        echo '$request null';
        die;
    }
    $fields = array('registration_ids' => $request['deviceIDs'], 'data' => array('message' => $request['message']));
    $headers = array('Content-Type: application/json',
            'Authorization: key=AIzaSyDhHHhwioLwheyWlQNw_oR67T-wRTrpR4o');
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, "https://android.googleapis.com/gcm/send");
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
    $result = curl_exec($ch);
    curl_close($ch);
    echo $result;
?>
<?php
$session_options = array(
		'keyPrefix' => '',
		//'lifetime'  => $config->timeout,
		'rediska'   => array(
				'servers' => array(
						array('host' => $config->REDIS_SERVER,'port' =>6379)
				)
		)
);

$app_options = array(
		
		'servers' => array(
				array('host' => $config->REDIS_SERVER,'port' =>6379),
				array('host' => $config->REDIS_SERVER_F,'alias'=>$config->FRONTEND_ALIAS,'port' =>6379)
		)
);
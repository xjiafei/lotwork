<?php


$start = date('Y-m-d', time());

echo $start . "<br/>";

$end = strtotime(date("Y-m-d", strtotime($start)) . " -1 month");
$end = date('Y-m-d', strtotime(date("Y-m-d", strtotime($start)) . " -1 month"));
echo $end . "<br/>";


$db = "10.3.7.20:1521/game";
$conn = oci_connect("us_firefog", "firefog_123qwe", $db);

$qry = "select t1.column_name, comments from all_tab_columns t1, all_col_comments t2 where t1.table_name = t2.table_name and t1.column_name = t2.column_name and t1.table_name = 'AD' ORDER BY COLUMN_ID";
$qry = "select * from AD";
$stmt = oci_parse($conn, $qry);
oci_execute($stmt, OCI_DEFAULT);
		

while (oci_fetch($stmt)) {
	//echo " [" . oci_result($stmt, "COMMENTS") . "]<br/>";
	echo " [" . oci_result($stmt, "NAME") . "]<br/>";
}	
		
		
		
?>
<?php
$array = array(
    "id" => 0,
    "type" => 'book',
    "name" => "JavaScript权威指南",
    "0" => array(
        'title' => "JavaScript权威指南",
        'author' => "Flanagan",
        'translator' => "淘宝前端团队",
        "price" => "￥100"),
    "1" => array(
        'title' => "JavaScript: The Definitive Guide",
        'author' => "Flanagan",
        "price" => "$30")
);
// $array = array(
//     '0' => array(
//         'id' => 1,
//         'name' => "aaa",
//         'version' => "1.0"),
//     '1' => array(
//         'id' => 2,
//         'name' => "bbb",
//         'version' => "1.1" ),
// );
switch (array_keys($_GET,0)[0]) {
    case 'json':
        JsonCreate($array);
        break;
    case 'xml':
        XMLCreate($array);
        break;

    default:
        break;
}

function JsonCreate($array = null) {
    if ($array == null) {
        return 0;
    }
    $json = json_encode($array);
    echo $json;
}

function XMLCreate($array = null) {
    if ($array == null) {
        return 0;
    }
    $xml = new XMLWriter();
    $xml->openUri("php://output");
    $xml->setIndentString("");
    $xml->setIndent(true);
    $xml->startDocument("1.0","utf-8");
    if(count( $array) > 1 ){
        $xml->startElement('item');
    }
    foreach ($array as $key => $data) {
        $xml->startElement('item');
        if (is_array($data)) {
            foreach ($data as $inkey => $row) {
                $xml->startElement($inkey);
                if (isset($attribute_array[$inkey]) && is_array($attribute_array[$inkey])) {
                    foreach ($attribute_array[$inkey] as $akey => $aval) {
                        $xml->writeAttribute($akey, $aval);
                    }
                }
                $xml->text($row);
                $xml->endElement();
            }
        } else {
            $xml->startElement($key);
            if (isset($attribute_array[$key]) && is_array($attribute_array[$key])) {
                foreach ($attribute_array[$key] as $bkey => $bval) {
                    $xml->writeAttribute($bkey, $bval);
                }
            }
            $xml->text($data);
            $xml->endElement();
        }
        $xml->endElement();
    }
    if(count( $array) > 1 ){
        $xml->endElement();
    }
    $xml->endElement();
    $xml->endDocument();
    $xml->flush();
}

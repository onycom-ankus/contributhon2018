import requests
from bs4 import BeautifulSoup as bs
import urllib.request
import os
import datetime
import csv

instargram_url = 'https://www.instagram.com/explore/tags/%EC%9E%90%EB%8F%99%EC%B0%A8/'
r = requests.get( instargram_url )
html = r.text


outpath = "C:/image_download/"

if not os.path.isdir(outpath):
    os.makedirs(outpath)

str_image_url_start = 'display_url":"'	    # image start tag
str_image_url_end   = '_n.jpg'              # image end tag

str_timestamp_start = 'taken_at_timestamp":'    # time stamp
str_timestamp_end = ','

str_comment_start = 'edge_media_to_comment":{"count":'
str_comment_end = '}'

str_like_start = 'edge_liked_by":{"count":'
str_like_end = '}'

dt = datetime.datetime.now()

ut_current = dt.timestamp()

img_count = html.count(str_image_url_start)
total_count = img_count + 1
# print(total_count)

ut_current1 = int(ut_current)
#print(ut_current1)

csv_outfile2 = repr(ut_current1)
csv_outfile = outpath + csv_outfile2 + ".csv"
fp = open(csv_outfile, 'w', encoding='utf8')
#print(csv_outfile)

image_position_start = 1
image_position_end = 1

lines = "time stamp, comment count, like count, image_name" + '\n'
fp.write(lines)
	
for i in range(1, total_count):
    
    # count of comment 
    pos_comment_start = html.find(str_comment_start, image_position_start) + 32
    pos_comment_end = html.find(str_comment_end, pos_comment_start )
    comment_count = html[pos_comment_start:pos_comment_end]
   # print( comment_count )
   # print(pos_comment_start)
   # print('~')
   # print(pos_comment_end)    
    
    # 이미지의 timestamp 확인
    pos_timestamp_start = html.find(str_timestamp_start, pos_comment_start) + 20
    pos_timestamp_end = html.find(str_timestamp_end, pos_timestamp_start )

    #print( pos_timestamp_start )
    #print( '-') 
    #print( pos_timestamp_end )
    pos_timestamp = html[pos_timestamp_start:pos_timestamp_end]
    #print( "[time stamp ]" )
    #print( pos_timestamp )
 
    # count of like 
    pos_like_start = html.find(str_like_start, pos_comment_start) + 24
    pos_like_end = html.find(str_like_end, pos_like_start )
    like_count = html[pos_like_start:pos_like_end]
    #print( like_count )
    
    # check image-link
    image_position_start = html.find(str_image_url_start, pos_comment_start) +14
    image_position_end = html.find(str_image_url_end, image_position_start) + 6

    # 이미지 주소 검색 및 저장
    display_url = html[image_position_start:image_position_end]
    #print("[display_url]")
    #print(display_url)
    
    outfile_name = display_url.split('/')
    #print(outfile_name)
    outfile_name_length = len(outfile_name) - 1
    #print("[outifle_name count]")
    #print(outfile_name_length)
    str6 = outfile_name[outfile_name_length]
    #print(str6)    
    
    outfile = outfile_name[outfile_name_length]
    # 이미지 다운로드
    urllib.request.urlretrieve(display_url, outpath + outfile)
    #print("[saved images's count]")
    #print( i )

    # 이미지 이름과 timestamp 저장 (csv 파일로)
    lines = pos_timestamp + ',' + comment_count + ',' + like_count + ',' + outfile + '\n'
    #print(lines)
    fp.write(lines)

fp.close()
print("Download complete" )


import json
import requests
import urllib.request
import time
import re
import os.path

f = open("movies.txt","r")
for line in f.readlines():
    company = line.split(",")[6]
    ok = 0
    m=re.sub('[^A-Za-z0-9]+', '', company)
    print(m)
    
    a = os.path.isfile(m+"poster.jpg") and os.path.isfile( m+"backdrop.jpg") 
    if a:
        print("Skipping")
        continue

    while ok < 5:
        try:
            ok+=1
            url = "https://advanced-movie-search.p.rapidapi.com/search/movie"

            querystring = {"query":company,"page":"1"}

            headers = {
                "X-RapidAPI-Key": "cf2651e6d4msh20d179562e563c1p11409cjsn9c42516909b4",
                "X-RapidAPI-Host": "advanced-movie-search.p.rapidapi.com"
            }

            response = requests.request("GET", url, headers=headers, params=querystring)
            j = json.loads(response.text)
            url1 = j['results'][0]['poster_path']
            print(url1)
            
            urllib.request.urlretrieve(url1, m+"poster.jpg")
            
            url2 = j['results'][0]['backdrop_path']
            print(url2)
            urllib.request.urlretrieve(url2 , m+"backdrop.jpg")

            break
        except:
            
            print("--ERROR---------- " + movie)
            time.sleep(3)
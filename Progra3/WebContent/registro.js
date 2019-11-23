fetch("https://jsonplaceholder.typicode.com/posts").then(
    res=>{
        res.json().then(
            data=>{
                if(true){
                    var temp="";

                    data.forEach((u)=>{
                        console.log("gello");
                        temp += "<tr>";
                        temp += "<td>"+ u.userId +"</td>";
                        temp += "<td>"+ u.id +"</td>";
                        temp += "<td>"+ u.title +"</td></tr>";
                    })

                    document.getElementById("datas").innerHTML= temp;
                }
            }
        )
    }
)
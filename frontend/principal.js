var table = document.querySelector('table#tabela'); // recebe tabela que vai receber daodos

var lista = [{"nomeAluno":"bisicreta","Curso":60,"matricula":"123","idAluno":2},
    {"nomeAluno":"homocinética","Curso":500,"matricula":"456","idAluno":3},
    {"nomeAluno":"baixo","Curso":2000,"matricula":"789","idAluno":4},
    {"nomeAluno":"notebook lenovo","Curso":500,"matricula":"000","idAluno":5},
    {"nomeAluno":"macaco","Curso":321,"matricula":"4002","idAluno":6},
    {"nomeAluno":"Viola Caipira","Curso":400.34,"matricula":"1010","idAluno":7},
    {"nomeAluno":"DiminuidAlunoor de cabeça","Curso":400,"matricula":"666","idAluno":8},
    {"nomeAluno":"produto foda","Curso":69,"matricula":"6969","idAluno":9},
    {"nomeAluno":"Xavleg","Curso":157,"matricula":"171","idAluno":11}];

window.onload = function(){

    var updateModal = function (msg){

        alert(msg);
        
    }

    var deleteAluno = function(id){

        confirm("Você deseja mesmo deletar esse aluno");

    }

    var readModal = function(matricula){

        criarModal();

    }

    printTable(table,lista,updateModal,deleteAluno,readModal);    

}

function printTable(table,lista,updateModal,deleteAluno,readModal) {

    //Abaixo criação de cabeçalho
    let thead = document.createElement('thead');
    let hrow = document.createElement('tr');
    let hdata = new Array();
    for(let i=0;i<Object.keys(lista[0]).length;i++) hdata[i] = document.createElement('th');
    hdata[0].innerText = Object.keys(lista[0])[3];
    hdata[1].innerText = Object.keys(lista[0])[2];
    hdata[2].innerText = Object.keys(lista[0])[0];
    hdata[3].innerText = Object.keys(lista[0])[1];
    for(let i=0;i<Object.keys(lista[0]).length;i++) hrow.appendChild(hdata[i]);
    thead.appendChild(hrow);
    table.appendChild(thead);
    
    //Abaixo criação do corpo da tabela
    let tbody = document.createElement('tbody');
    for(let i in lista) {
        let row = document.createElement('tr');
        row.setAttribute('class','datarow');
        if(i%2!==0) row.setAttribute('style','background-color: rgb(230, 230, 230);');
        let data = new Array();
        for(let i=0;i<Object.keys(lista[0]).length;i++) {
            data[i] = document.createElement('td');
        }
        data[0].innerText = `${lista[i].idAluno}`;
        row.appendChild(data[0]);
        data[1].innerText = `${lista[i].matricula}`;
        row.appendChild(data[1]);
        data[2].innerText = `${lista[i].nomeAluno}`;
        row.appendChild(data[2]);
        data[3].innerText = `${lista[i].Curso}`;
        row.appendChild(data[3]);

        var updateIcon = document.createElement('img');
        updateIcon.setAttribute("src", "../images/update.png");
        //updateIcon.addEventListener("click",updateModal(lista[i].nomeAluno));

        var zoomIcon = document.createElement('img');
        zoomIcon.setAttribute("src", "../images/zoom.png");

        var deleteIcon  = document.createElement('img');;
        deleteIcon.setAttribute("src", "../images/lixeira.png");

        var update = document.createElement('td');
        update.setAttribute("class","updateButtton");
        update.addEventListener("click",function(){

            updateModal(lista[i].nomeAluno);
            
        });
        update.appendChild(updateIcon);
        row.appendChild(update);
        
        var readAll = document.createElement('td');
        readAll.setAttribute("class","getAllButtton");
        readAll.addEventListener("click",function(){

            readModal();

        });
        readAll.appendChild(zoomIcon);
        row.appendChild(readAll);

        var _delete = document.createElement('td');
        _delete.setAttribute("class","_deleteButtton");
        _delete.addEventListener("click",function(){

            deleteAluno(lista[i].idAluno);

        });
        _delete.appendChild(deleteIcon);
        row.appendChild(_delete);

        tbody.appendChild(row);
        
    }

    table.appendChild(tbody);

}

function criarModal(){

    console.log("FUUNFO");

}
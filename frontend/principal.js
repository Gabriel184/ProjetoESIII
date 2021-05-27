var table = document.querySelector('table#tabela'); // recebe tabela que vai receber daodos

var lista = [{"descricao":"bisicreta","valor":60,"codigo":"123","id":2},
    {"descricao":"homocinética","valor":500,"codigo":"456","id":3},
    {"descricao":"baixo","valor":2000,"codigo":"789","id":4},
    {"descricao":"notebook lenovo","valor":500,"codigo":"000","id":5},
    {"descricao":"macaco","valor":321,"codigo":"4002","id":6},
    {"descricao":"Viola Caipira","valor":400.34,"codigo":"1010","id":7},
    {"descricao":"Diminuidor de cabeça","valor":400,"codigo":"666","id":8},
    {"descricao":"produto foda","valor":69,"codigo":"6969","id":9},
    {"descricao":"Xavleg","valor":157,"codigo":"171","id":11}];

    printTable(table,lista);

function printTable(table,lista) {

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
        data[0].innerText = `${lista[i].id}`;
        row.appendChild(data[0]);
        data[1].innerText = `${lista[i].codigo}`;
        row.appendChild(data[1]);
        data[2].innerText = `${lista[i].descricao}`;
        row.appendChild(data[2]);
        data[3].innerText = `${lista[i].valor}`;
        row.appendChild(data[3]);
        tbody.appendChild(row);
        
    }

    table.appendChild(tbody);

}
var table = document.querySelector('table#tabela'); // recebe tabela que vai receber daodos

function printTable(table,lista,updateModal,deleteAluno,readModal) {
    //Abaixo criação de cabeçalho
    let thead = document.createElement('thead')
    let hrow = document.createElement('tr')
    let hdata = new Array()
    for(let i=0;i<Object.keys(lista).length;i++)
        hdata[i] = document.createElement('th')
    hdata[0].innerText = Object.keys(lista[0])[1]
    hdata[1].innerText = Object.keys(lista[0])[2]
    hdata[2].innerText = Object.keys(lista[0])[3]
    hdata[3].innerText = Object.keys(lista[0])[4]
    hdata[4].innerText = Object.keys(lista[0])[5]
    hdata.forEach(hd => {
        hrow.appendChild(hd)
    })
    thead.appendChild(hrow)
    table.appendChild(thead)

    let tbody = document.createElement('tbody')
    lista.forEach(item => {
        let row = document.createElement('tr')
        let data = new Array()
        for(let i in Object.keys(item))
            data.push(document.createElement('td'))
        data[0].innerText = `${
            item.dataCadastro.dayOfMonth + '/' +
            (item.dataCadastro.month+1) + '/' +
            item.dataCadastro.year
        }`
        row.appendChild(data[0])
        data[1].innerText = `${item.nome}`
        row.appendChild(data[1])
        data[2].innerText = `${item.email}`
        row.appendChild(data[2])
        data[3].innerText = `${item.cidade}`
        row.appendChild(data[3])
        data[4].innerText = `${item.curso}`
        row.appendChild(data[4])

        var updateIcon = document.createElement('img')
        updateIcon.setAttribute("src", "../images/update.png")

        var zoomIcon = document.createElement('img')
        zoomIcon.setAttribute("src", "../images/zoom.png")

        var deleteIcon  = document.createElement('img')
        deleteIcon.setAttribute("src", "../images/lixeira.png")

        var update = document.createElement('td')
        update.setAttribute("class","updateButtton")
        update.addEventListener("click",function(){
            updateModal(item.codigo)
        })
        update.appendChild(updateIcon)
        row.appendChild(update)
        
        var readAll = document.createElement('td')
        readAll.setAttribute("class","getAllButtton")
        readAll.addEventListener("click",function(){
            readModal(item.codigo)
        })
        readAll.appendChild(zoomIcon)
        row.appendChild(readAll)

        var _delete = document.createElement('td')
        _delete.setAttribute("class","_deleteButtton")
        _delete.addEventListener("click",function(){
            deleteAluno(item.codigo)
        })
        _delete.appendChild(deleteIcon)
        row.appendChild(_delete)

        tbody.appendChild(row)
    })
    table.appendChild(tbody)
}

function criarModal(modalId) {
    var modalRead = document.getElementById(modalId)
    modalRead.style.display = 'block'
}

window.onload = function() {
    var updateModal = function (codigo){
        criarModal('container_modal_update')
        $.ajax({
            url : '/e-matricula/Matricula',
            type : 'get',
            data : {
                operacao : 'consultar',
                codigo : codigo
            },
            success : function(msg) {
                var matricula = msg.mensagem
                
                // dados de aluno
                var aluno = matricula.aluno
                document.getElementById('upd_cpf').value = aluno.cpf
                document.getElementById('upd_nome').setAttribute('placeholder',aluno.nome)
                document.getElementById('upd_email').setAttribute('placeholder',aluno.email)
                document.getElementById('upd_telefone').setAttribute('placeholder',aluno.telefone)
                
                // dados endereços
                var endereco = aluno.endereco
                document.getElementById('upd_cep').setAttribute('placeholder',endereco.cep)
                document.getElementById('upd_logradouro').setAttribute('placeholder',endereco.logradouro)
                document.getElementById('upd_numero').setAttribute('placeholder',endereco.numero)
                document.getElementById('upd_complemento').setAttribute('placeholder',(endereco.complemento == '') ? 'N/A' : endereco.complemento)
                document.getElementById('upd_bairro').setAttribute('placeholder',endereco.bairro)
                document.getElementById('upd_cidade').setAttribute('placeholder',endereco.cidade.descricao)
                document.getElementById('upd_estado').setAttribute('placeholder',endereco.cidade.estado.descricao)

                document.getElementById('upd_submit').onclick = function() {
                    if(confirm('Deseja mesmo atualizar os dados?')) {
                        $.ajax({
                            url : '/e-matricula/Matricula',
                            type : 'get',
                            data : {
                                operacao : 'atualizar',
                                cpf : document.getElementById('upd_cpf').value,
                                nome : document.getElementById('upd_nome').value,
                                email : document.getElementById('upd_email').value,
                                telefone : document.getElementById('upd_telefone').value,
                                cep : document.getElementById('upd_cep').value,
                                logradouro : document.getElementById('upd_logradouro').value,
                                numero : document.getElementById('upd_numero').value,
                                complemento : document.getElementById('upd_complemento').value,
                                bairro : document.getElementById('upd_bairro').value,
                                cidade : document.getElementById('upd_cidade').value,
                                estado : document.getElementById('upd_estado').value
                            },
                            success : function(msg) {
                                if(msg.status == 'success') {
                                    alert(msg.mensagem)
                                    location.reload()
                                } else {
                                    alert(msg.mensagem)
                                }
                            }
                        })
                    }
                }
            }
        })
    }

    var deleteAluno = function(codigo) {
        if(confirm('Você deseja mesmo deletar esse aluno')) {
            $.ajax({
                url : '/e-matricula/Matricula',
                type : 'get',
                data : {
                    operacao : 'excluir',
                    codigo : codigo
                },
                success : function(msg) {
                    if(msg.status == 'success') {
                        alert(msg.mensagem)
                        location.reload()
                    } else {
                        alert(msg.mensagem)
                    }
                }
            })
        }
    }

    var readModal = function(codigo) {
        criarModal('container_modal_read')
        $.ajax({
            url : '/e-matricula/Matricula',
            type : 'get',
            data : {
                operacao : 'consultar',
                codigo : codigo
            },
            success : function(msg) {
                // dados primitivos de matrícula
                var matricula = msg.mensagem
                var dataCadastro = matricula.dataCadastro
                document.getElementById('data_cadastro').innerText = 
                    `${dataCadastro.dayOfMonth + '/' + 
                        (dataCadastro.month+1) + '/' +
                        dataCadastro.year}`
                document.getElementById('turma').innerText = matricula.turma
                
                // dados de aluno
                var aluno = matricula.aluno
                document.getElementById('nome').innerText = aluno.nome
                document.getElementById('email').innerText = aluno.email
                document.getElementById('cpf').innerText = aluno.cpf
                document.getElementById('telefone').innerText = aluno.telefone

                var dataNascimento = aluno.dataNascimento
                document.getElementById('data_nascimento').innerText = 
                    `${dataNascimento.dayOfMonth + '/' + 
                        (dataNascimento.month+1) + '/' +
                        dataNascimento.year}`
                
                // dados endereços
                var endereco = aluno.endereco
                document.getElementById('logradouro').innerText = endereco.logradouro
                document.getElementById('numero').innerText = endereco.numero
                document.getElementById('bairro').innerText = endereco.bairro
                document.getElementById('complemento').innerText = (endereco.complemento == '') ? 'N/A' : endereco.complemento
                document.getElementById('cidade').innerText = endereco.cidade.descricao
                document.getElementById('cep').innerText = endereco.cep
                document.getElementById('estado').innerText = endereco.cidade.estado.descricao

                var curso = matricula.curso
                // dados de curso
                document.getElementById('categoria').innerText = curso.categoria.descricao
                document.getElementById('curso_nome').innerText = curso.descricao
                document.getElementById('duracao').innerText = `${curso.duracao + ' semestres'}`
            }
        })
    }

    $.ajax({
        url : '/e-matricula/Matricula',
        type : 'get',
        data : {
            operacao : 'get'
        },
        success : function(msg) {
            $('#resultado').html('')
            if(msg.status == 'success') {
                printTable(table,msg.mensagem,updateModal,deleteAluno,readModal)
            } else {
                alert(msg.mensagem)
            }
        }
    })
}
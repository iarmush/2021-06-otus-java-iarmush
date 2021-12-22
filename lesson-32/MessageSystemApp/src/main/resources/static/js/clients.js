let errorMessageSave = "Для сохранения пользователя необходимо подключение к веб-сокетам"
let errorMessageEdit = "Для изменения пользователя необходимо подключение к веб-сокетам"
let stompClient = null
let connected = false
$(document).ready(function () {
  switchConnection(false)
});

const switchConnection = (connect) => {
  connected = connect;
  $("#connect").prop("disabled", connected)
  $("#disconnect").prop("disabled", !connected)
}

const connect = () => {
  switchConnection(true)
  stompClient = Stomp.over(new SockJS('/ws'))
  stompClient.connect({}, (frame) => {
    console.log('Connected: ' + frame)
    stompClient.subscribe('/topic/client/response/save',
        (response) => displayClient(JSON.parse(response.body)))
    stompClient.subscribe('/topic/client/response/edit',
        (response) => displayEditedClient(JSON.parse(response.body)))
  })
}

const disconnect = () => {
  if (stompClient !== null) {
    stompClient.disconnect()
  }
  switchConnection(false)
  console.log("Disconnected")
}

const clientSave = () => {
  const client = {
    login: document.getElementById('client-login-save').value,
    role: document.getElementById('client-role-save').value

  };
  if (connected) {
    stompClient.send("/app/save", {}, JSON.stringify(client))
  } else {
    alert(errorMessageSave);
  }
}

const clientEdit = () => {
  const client = {
    id: document.getElementById('client-id-edit').value,
    login: document.getElementById('client-login-edit').value,
    role: document.getElementById('client-role-edit').value

  };
  if (connected) {
    stompClient.send("/app/edit", {}, JSON.stringify(client))
  } else {
    alert(errorMessageEdit);
  }
}

async function getClients() {
  const response = await fetch('/api/clients', {
    method: "GET",
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
  });
  const savedClient = await response.json()
  displayClients(savedClient)
}

const displayClients = (response) => {
  window.document.getElementById("clients").innerHTML = null
  let clientList = JSON.parse(JSON.stringify(response))
  for (const client of clientList) {
    displayClient(client)
  }
}

const displayEditedClient = (client) => {
  $('#' + client.id).remove();
  displayClient(client)
}

const displayClient = (client) => {
  $('#clients').append(`
               <tr id="${client.id}">
               <td>${client.id}</td>
               <td>${client.role}</td>
               <td>${client.login}</td>
               </tr>
        `)
}

function hideClients() {
  $("#clients").empty();
}

$(function () {
  $("form").on('submit', (event) => {
    event.preventDefault()
  });
  $("#connect").click(connect)
  $("#disconnect").click(disconnect)
  $("#clients-get").click(getClients)
  $("#clients-hide").click(hideClients)
  $("#client-save").click(clientSave)
  $("#client-edit").click(clientEdit)
});
function run(){
    let socket = new WebSocket
    ('ws://'+location.hostname+(location.port ? ':'+location.port: '')+'/ws');

    socket.onmessage = event => {
        let msg = JSON.parse(event.data);
        $('#logs').append(msg).append("\n");
    };
}

run();
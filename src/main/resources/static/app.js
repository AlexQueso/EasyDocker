function run(){
    let socket = new WebSocket ('wss://'+location.hostname+(location.port ? ':'+location.port: '')+'/ws');
    socket.onmessage = event => {
        $('#logs').append(event.data).append("\n");
    };
}
run();
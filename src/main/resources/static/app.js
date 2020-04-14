function run(){
    var textarea = document.getElementById("logs");
    let socket = new WebSocket ('wss://'+location.hostname+(location.port ? ':'+location.port: '')+'/ws');
    socket.onmessage = event => {
        $('#logs').append(event.data).append("\n");

        if(textarea.selectionStart === textarea.selectionEnd) {
             textarea.scrollTop = textarea.scrollHeight;
        }
    };
}
run();

function generateNumbers(){
    let array = new Uint8Array(6);
    window.crypto.getRandomValues(array);
    let i;
    for (i=0;i<array.length;i++) {
        document.getElementById("number" + (i + 1).toString()).value = array[i] % 61; // including 60
    }
}
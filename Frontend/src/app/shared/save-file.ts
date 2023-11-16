export function saveAs(blob: Blob, fileName: string) {
    const downloadAncher = document.createElement("a");
    downloadAncher.style.display = "none";

    const fileURL = URL.createObjectURL(blob);
    downloadAncher.href = fileURL;
    downloadAncher.download = fileName + '.pdf';
    downloadAncher.click();
}
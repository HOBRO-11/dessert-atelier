const DEFAULT_EXPORT_PATH = "";

module.exports = {
    entry: async (params, settings) => {
        const {
            app,
            quickAddApi,
            obsidian: { Notice },
        } = params;

        // 캔버스 콘텐츠 요소 복사
        let view = app.workspace.activeLeaf.view;
        if (view.getViewType() !== "canvas") {
            new Notice("현재 활성화된 뷰는 캔버스가 아닙니다", 5000);
            return; // 캔버스가 아닐 경우 종료
        }
        let content = view.contentEl.cloneNode(true);

        // 캔버스 배경 점 및 UI 제어 요소 제거
        content.querySelector(".canvas-background").remove();
        content.querySelector(".canvas-card-menu").remove();
        content.querySelector(".canvas-controls").remove();
        content.querySelectorAll(".canvas-node-label").forEach((el) => el.remove());

        // 인쇄 스타일을 제외한 모든 CSS 가져오기
        let allCSS = [...document.styleSheets]
            .map((styleSheet) =>
                [...styleSheet.cssRules]
                    .map((rule) => rule.cssText)
                    .filter((cssText) => !cssText.includes("@media print")) // 인쇄 스타일 제외
                    .join("\n")
            )
            .join("\n");

        // HTML 및 CSS 생성
        let html = `
    <!DOCTYPE HTML>
    <html>
    <meta charset="UTF-8">
    <head>
    <style>
    ${allCSS}
    /* 카드 배경과 글머리의 색상 조정 */
    body { -webkit-print-color-adjust: exact; print-color-adjust: exact; }
    </style>
    </head>
    <body class="${document.querySelector("body").className}">
    ${content.outerHTML}
    </body>
    </html>`;

        // 현재 시간 및 캔버스 파일명 가져오기
        const canvasFileName = view.file.name.replace(/\.[^/.]+$/, ""); // 확장자 제거
        const canvasDirectory = view.file.parent.path; // 캔버스의 디렉토리 경로
        const directory = `export/${canvasDirectory}`; // HTML 파일 경로 생성
        const filename = `${directory}/${canvasFileName}.html`; // HTML 파일 경로

        // 경로의 폴더가 존재하는지 확인하고, 없으면 생성
        const folderExists = app.vault.getAbstractFileByPath(directory);

        if (!folderExists) {
            await app.vault.createFolder(directory); // 폴더 생성
        }

        // HTML 파일 저장
        let existingFile = app.vault.getAbstractFileByPath(filename);
        if (existingFile) {
            await app.vault.modify(existingFile, html);
            new Notice(`수정이 완료되었습니다: ${filename}`, 5000);
        } else {
            await app.vault.create(filename, html);
            new Notice(`저장이 완료되었습니다: ${filename}`, 5000);
        }
    },
    settings: {
        name: "(정상동작하지 않음)",
        author: "rrherr",
        options: {
            "Export path": {
                type: "text",
                defaultValue: DEFAULT_EXPORT_PATH,
            }
        }
    }
};
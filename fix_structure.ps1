# fix_structure.ps1
# 修正后的脚本，请完整复制覆盖原内容

Write-Host "=== 开始调整目录结构 ===" -ForegroundColor Cyan

# 1. 定义深层源码的路径
$deepSource = ".\online_class-master\onlineclass"

# 2. 判断深层目录是否存在
if (Test-Path $deepSource) {
    Write-Host "检测到多余的嵌套目录，正在扁平化..." -ForegroundColor Yellow
    
    # 移动深层目录下的所有内容到当前根目录
    Get-ChildItem -Path $deepSource | Move-Item -Destination . -Force
    Write-Host "核心文件移动完成。" -ForegroundColor Green
    
    # 清理空的旧目录
    Write-Host "正在清理旧的空目录..." -ForegroundColor Yellow
    if (Test-Path ".\online_class-master") { 
        Remove-Item -Path ".\online_class-master" -Recurse -Force 
    }
    
    # 清理 IDE 自动生成的缓存文件 (可选)
    if (Test-Path ".\.idea") { Remove-Item -Path ".\.idea" -Recurse -Force }
    if (Test-Path ".\target") { Remove-Item -Path ".\target" -Recurse -Force }
    if (Test-Path ".\onlineclass.iml") { Remove-Item -Path ".\onlineclass.iml" -Force }
    
    Write-Host "清理完成。" -ForegroundColor Green
}

# 3. 如果没找到深层目录，检查是否已经调整好了
if (-not (Test-Path $deepSource)) {
    if (Test-Path ".\src\main\java") {
        Write-Host "检测到 src 目录已在根目录下，结构看起来是正确的！" -ForegroundColor Green
    } else {
        Write-Host "提示：未找到源码目录，请确认你是在 online_class 文件夹内部运行此脚本。" -ForegroundColor Gray
    }
}

Write-Host ""
Write-Host "=== 脚本运行结束 ===" -ForegroundColor Cyan
Write-Host "下一步：请用 IDEA 打开当前文件夹，并修正包名 Impl -> impl"
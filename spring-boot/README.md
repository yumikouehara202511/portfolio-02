# Spring Boot Migration Notes

このフォルダは、現在の静的サイトをSpring Bootへ移行するための準備領域です。

## Current Source Mapping

- `public-site/*.html` -> `src/main/resources/templates/public/`
- `admin/*.html` and `admin/reservations/index.html` -> `src/main/resources/templates/admin/`
- `public-site/assets/` -> `src/main/resources/static/assets/public/`
- `admin/assets/` -> `src/main/resources/static/assets/admin/`
- `spring-boot/docs/`, `spring-boot/design/`, `spring-boot/prompts/` -> 要件・設計・実装メモ

## Current Implementation

- 管理画面トップ: `/admin`
- 管理ログイン: `/admin/login`
- 予約一覧: `/admin/reservations`
- 予約詳細: `/admin/reservations/{id}`
- 予約作成: `/admin/reservations/new`
- 予約編集: `/admin/reservations/{id}/edit`
- H2 Console: `/h2-console`

## Run

```powershell
mvn spring-boot:run
```

MySQLで起動する場合:

```powershell
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

環境変数 `MYSQL_URL`, `MYSQL_USER`, `MYSQL_PASSWORD` で接続先を差し替えできます。

## Suggested Next Steps

1. Spring Securityで管理画面ログインを本実装にする。
2. 顧客、空き枠、分析を予約テーブルから分離してEntity化する。
3. FlywayまたはLiquibaseを追加し、H2/MySQL共通のマイグレーションで管理する。
4. 公開サイト側の予約フォームを管理画面の予約テーブルへ接続する。

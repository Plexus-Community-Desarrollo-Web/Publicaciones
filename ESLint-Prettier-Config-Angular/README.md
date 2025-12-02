# ✨ Guía de ESLint + Prettier para Angular

Esta guía describe una configuración clara y mantenible para formateo (Prettier) y análisis estático (ESLint) en proyectos Angular. El objetivo es asegurar consistencia, legibilidad y calidad del código en entornos de trabajo. 🌈

## 🚀 Instalación

### 1) Añadir soporte ESLint para Angular

Reemplaza `18` por la versión de Angular utilizada en el proyecto:

```bash
ng add @angular-eslint/schematics@18
```

### 2) Instalar dependencias de Prettier y ESLint

```bash
npm install prettier prettier-eslint eslint-config-prettier eslint-plugin-prettier typescript-eslint eslint-plugin-import eslint-plugin-unused-imports --save-dev
```

## 🛠 Configuración

### Archivos de configuración

Ubica en la raíz del proyecto:

- `.prettierrc` → reglas de estilo (comillas, ancho de línea, etc.)
- `.prettierignore` → archivos excluidos de Prettier
- `eslint.config.js` → se crea automáticamente al añadir `@angular-eslint`; es necesario completar su contenido con la configuración deseada (las reglas propuestas son opcionales y pueden ajustarse a preferencia del equipo).

Nota: Los tres archivos mencionados ya están presentes en este repositorio y pueden copiarse directamente a otros proyectos si se desea reutilizar la configuración.

### ⚙️ Configuración de VS Code (dos opciones)

Para que el guardado y el pegado automaticen correcciones de ESLint/Prettier, añade estas preferencias en VS Code. Puedes hacerlo de dos maneras:

1. Configuración por proyecto (recomendado)

- Crea el archivo `.vscode/settings.json` en la raíz del proyecto.
- Copia este contenido:

```json
{
  "editor.formatOnPaste": true,
  "editor.codeActionsOnSave": {
    "source.fixAll.eslint": "explicit",
    "source.fixAll": "explicit",
    "source.sortMembers": "explicit"
  }
}
```

2. Configuración global de VS Code

- Abre VS Code → File > Preferences > Settings.
- Usa el botón “Open Settings (JSON)” o busca “settings.json” y abre el archivo de usuario.
- Pega el mismo bloque JSON en el objeto raíz.

Notas:

- `source.fixAll.eslint: "explicit"` aplica correcciones de ESLint al guardar cuando se ejecutan acciones de código.
- `source.fixAll` cubre correcciones de otros proveedores que soporten fixAll.
- `source.sortMembers` ordena automáticamente miembros (si la extensión lo soporta).
- `eslint.config.js` → configuración completa de ESLLint (más detallada que la generada por defecto).

Importante:

- Todas las reglas y ajustes propuestos en esta guía son opcionales. Cada equipo puede adaptarlos según sus necesidades, añadiendo o quitando reglas en `eslint.config.js` y ajustando preferencias en `.prettierrc` y VS Code.

Añade este script para formatear el proyecto:

```json
"prettier": "npx prettier --write ."
```

Ejemplo de `package.json` (sección `scripts`):

```json
{
  "scripts": {
    "ng": "ng",
    "start": "ng serve",
    "build": "ng build",
    "watch": "ng build --watch --configuration development",
    "test": "ng test",
    "lint": "ng lint",
    "prettier": "npx prettier --write ."
  }
}
```

## 🎯 Uso rápido

- Formateo con Prettier:

  ```bash
  npm run prettier
  ```

- Ejecución de lint (Angular/ESLint):

  ```bash
  npm run lint
  ```

## 🧪 Reglas más flexibles (solo en tests)

Para agilizar la escritura de pruebas, en `eslint.config.js` se aplican reglas relajadas únicamente a archivos `*.spec.ts` y `*.test.ts`. Ejemplos:

- Permitir `any` y patrones "unsafe" (útil para mocks y stubs)
- No exigir tipo de retorno explícito
- Permitir variables/parámetros no usados en escenarios Given/When/Then
- Permitir uso de consola para depuración (`console.log` permitido)
- Flexibilizar validaciones de imports

Estas reglas no afectan al código de aplicación; únicamente a los tests. Así se mantiene un código de producción estricto y pruebas ágiles. ✅

## 📎 Notas

- La configuración de `eslint.config.js` en este repositorio es más completa que la generada automáticamente por Angular.
- Si se requiere mayor flexibilidad en tests, se puede desactivar `prettier` en esa sección del config (`prettier/prettier: 'off'`).
- Recuerda: todas las reglas añadidas pueden personalizarse. Ajusta, añade o elimina según el criterio del equipo y las políticas del proyecto.

---

Listo. Con esta configuración tendrás un código consistente, legible y fácil de mantener. 🎉

# Etapa 1: Construção do Angular
FROM node:22-alpine AS build

# Diretório de trabalho dentro do container
WORKDIR /app

# Copia os arquivos necessários para a construção
COPY . .

# Instalar as dependências do projeto
RUN npm install

# Instalar o Angular CLI globalmente (opcional)
RUN npm install -g @angular/cli

# Executar o build do Angular
RUN npm run build-prod

# Etapa 2: Servir a aplicação com Nginx
FROM nginx:alpine

# Copia os arquivos compilados do Angular para o diretório do Nginx
COPY --from=build /app/dist/barber-shop-ui/browser /usr/share/nginx/html

# Copia a configuração personalizada do Nginx
COPY nginx.conf /etc/nginx/nginx.conf

# Expor a porta 80 para acessar a aplicação
EXPOSE 80

# Iniciar o Nginx
CMD ["nginx", "-g", "daemon off;"]

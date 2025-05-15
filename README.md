# Pipeline de Deploy Automatizado – Tech Challenge

## Foco do Projeto

Este projeto tem como principal objetivo demonstrar a criação de uma infraestrutura automatizada na AWS para hospedar uma aplicação Java com banco de dados MySQL, utilizando práticas modernas de DevOps e infraestrutura como código (IaC). Toda a camada de infraestrutura foi provisionada com Terraform e integrada com CI/CD via GitHub Actions.

> ⚠️ O backend da aplicação (Java + MySQL) já foi entregue pela equipe de desenvolvimento e **não foi o foco deste trabalho**.

---

## Tecnologias utilizadas

- **Terraform** → Provisionamento da infraestrutura na AWS
- **GitHub Actions** → Orquestração do CI/CD
- **AWS EC2 (t3.small)** → Instância Linux para execução da aplicação
- **Docker + Docker Compose** → Empacotamento e orquestração dos containers
- **SSH + SCP** → Sincronização de arquivos e comandos remotos
- **Swagger** → Interface pública para teste da API

---

## Infraestrutura Provisionada com Terraform

- VPC customizada com sub-rede pública
- Internet Gateway e Tabela de Rotas
- Instância EC2 (Amazon Linux 2023) com IP público associado
- Grupo de segurança com portas liberadas para aplicação
- Criação de par de chaves para acesso SSH
- Exportação automática do IP público via `terraform output`

---

## Pipeline Automatizado (GitHub Actions)

1. Push na branch `main`
2. Workflow CI/CD executa:
   - `terraform apply` com as credenciais seguras
   - Captura o IP público da EC2 (`terraform output`)
   - Sincroniza os arquivos via `scp` com chave SSH segura
   - Executa `docker-compose up -d --build` remotamente
3. Aplicação é atualizada automaticamente na instância

---

## Estrutura esperada do repositório

``` shell
.
├── .github/
│   └── workflows/
│       └── terraform-apply.yml  # CI/CD completo
├── terraform/
│   ├── main.tf
│   ├── outputs.tf
│   └── variables.tf
├── docker/
│   └── docker-compose.yml
├── app/
│   └── #código Java fornecido pela equipe de desenvolvimento
```

---

## Secrets e Variáveis no GitHub

| Nome                 | Tipo   | Descrição                                |
|----------------------|--------|--------------------------------------------|
| `TF_VAR_DB_USERNAME` | Secret | Usuário do banco de dados (MySQL)          |
| `TF_VAR_DB_PASSWORD` | Secret | Senha do banco de dados                    |
| `TF_VAR_PUBLIC_KEY`  | Secret | Chave pública para acesso SSH na EC2       |
| `EC2_SSH_KEY`        | Secret | Chave privada usada no `scp` e SSH remoto  |

---

## Acesso à Aplicação

Após o pipeline:

```text
http://<IP_DA_INSTANCIA>:<PORTA>/docs
```

(Ex: Interface Swagger para testes de API)

---

## Observações importantes

- O `--build` no `docker-compose` garante que o container Java seja recriado com as mudanças
- O IP da EC2 é obtido via `terraform output -raw ec2_instance_ip`
- O deploy ocorre **automaticamente** a cada push na branch `main`
- A aplicação pode ser acessada via:  
  `http://<IP_DA_INSTANCIA>:<PORTA>/docs` (ex: Swagger UI)

## Destaques e Boas Práticas

- **Infraestrutura modular**: Código Terraform organizado por arquivos e reutilizável
- **CI/CD automatizado**: Deploy 100% automático via GitHub Actions
- **Chaves seguras**: Nenhuma chave sensível está presente no código
- **Escalável**: A infraestrutura pode ser expandida para ambientes maiores
- **Segurança**: O banco de dados está privado com acesso apenas para a instância da aplicação.

---

## 📌 Considerações finais

Este projeto demonstrou o provisionamento completo de uma stack de aplicação na AWS com foco em automação, segurança e boas práticas de DevOps. A entrega considerou todos os requisitos da POC, com destaque para o uso de **Terraform, GitHub Actions e Docker em produção**.
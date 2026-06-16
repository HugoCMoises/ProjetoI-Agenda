const API_URL = "http://localhost:8080/contatos";

const form = document.getElementById("formContato");
const contatoId = document.getElementById("contatoId");
const nome = document.getElementById("nome");
const telefone = document.getElementById("telefone");
const email = document.getElementById("email");
const campoBusca = document.getElementById("campoBusca");
const listaContatos = document.getElementById("listaContatos");
const mensagem = document.getElementById("mensagem");
const botaoSalvar = document.getElementById("botaoSalvar");
let contatosNaTela = [];

document.getElementById("botaoCancelar").addEventListener("click", limparFormulario);
document.getElementById("botaoBuscar").addEventListener("click", buscarContato);
document.getElementById("botaoListar").addEventListener("click", listarContatos);

telefone.addEventListener("input", () => {
    telefone.value = telefone.value.replace(/\D/g, "");
});

form.addEventListener("submit", async (event) => {
    event.preventDefault();

    const erro = validarFormulario();
    if (erro) {
        mostrarMensagem(erro);
        return;
    }

    const contato = {
        nome: nome.value.trim(),
        telefone: telefone.value.trim(),
        email: email.value.trim()
    };

    const salvou = contatoId.value
        ? await enviar(`${API_URL}/${contatoId.value}`, "PUT", contato, "Contato atualizado com sucesso.")
        : await enviar(API_URL, "POST", contato, "Contato cadastrado com sucesso.");

    if (salvou) {
        limparFormulario();
        listarContatos();
    }
});

async function enviar(url, metodo, contato, textoSucesso) {
    const resposta = await requisicao(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(contato)
    });

    if (resposta.ok) {
        mostrarMensagem(textoSucesso, true);
        return true;
    }

    return false;
}

async function buscarContato() {
    const nomeBusca = campoBusca.value.trim();

    if (nomeBusca === "") {
        mostrarMensagem("Digite um nome para buscar.");
        return;
    }

    const resposta = await requisicao(`${API_URL}/buscar?nome=${encodeURIComponent(nomeBusca)}`);
    if (resposta.ok) {
        mostrarContatos([resposta.dados]);
        mostrarMensagem("Contato encontrado.", true);
    }
}

async function listarContatos() {
    const resposta = await requisicao(API_URL);

    if (resposta.ok) {
        mostrarContatos(resposta.dados);
    }
}

async function requisicao(url, opcoes = {}) {
    try {
        const resposta = await fetch(url, opcoes);
        const dados = await resposta.json().catch(() => null);

        if (!resposta.ok) {
            mostrarMensagem(dados?.mensagem || "Nao foi possivel concluir a operacao.");
            return { ok: false };
        }

        return { ok: true, dados };
    } catch (erro) {
        mostrarMensagem("Nao foi possivel conectar com o backend.");
        return { ok: false };
    }
}

function validarFormulario() {
    const nomeValor = nome.value.trim();
    const telefoneValor = telefone.value.trim();
    const emailValor = email.value.trim();

    if (!nomeValor || !telefoneValor || !emailValor) {
        return "Preencha todos os campos.";
    }

    if (nomeValor.length < 3 || /\d/.test(nomeValor)) {
        return "Preencha o nome corretamente.";
    }

    if (!/^\d+$/.test(telefoneValor)) {
        return "O telefone deve conter apenas numeros.";
    }

    if (telefoneValor.length < 10 || telefoneValor.length > 11) {
        return "O telefone deve ter entre 10 e 11 digitos.";
    }

    if (!/^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/.test(emailValor)) {
        return "Digite um e-mail valido.";
    }

    return "";
}

function mostrarContatos(contatos) {
    if (!contatos || contatos.length === 0) {
        listaContatos.innerHTML = "<p>Nenhum contato cadastrado.</p>";
        return;
    }

    contatosNaTela = contatos;
    listaContatos.innerHTML = contatos.map((contato, indice) => `
        <div class="contato">
            <div>
                <strong>${contato.nome}</strong>
                <span>${contato.telefone}</span>
                <span>${contato.email}</span>
            </div>
            <div class="acoes">
                <button class="editar" onclick="preencherFormulario(${indice})">Editar</button>
                <button class="excluir" onclick="excluirContato(${indice})">Excluir</button>
            </div>
        </div>
    `).join("");
}

function preencherFormulario(indice) {
    const contato = contatosNaTela[indice];

    contatoId.value = contato.id;
    nome.value = contato.nome;
    telefone.value = contato.telefone;
    email.value = contato.email;
    botaoSalvar.textContent = "Salvar alteracao";
    mostrarMensagem("Edite os dados e clique em salvar.");
}

async function excluirContato(indice) {
    const contato = contatosNaTela[indice];
    const resposta = await requisicao(`${API_URL}/${encodeURIComponent(contato.nome)}`, {
        method: "DELETE"
    });

    if (resposta.ok) {
        mostrarMensagem("Contato removido com sucesso.", true);
        listarContatos();
    }
}

function limparFormulario() {
    form.reset();
    contatoId.value = "";
    botaoSalvar.textContent = "Cadastrar";
}

function mostrarMensagem(texto, sucesso = false) {
    mensagem.textContent = texto;
    mensagem.classList.toggle("sucesso", sucesso);
}

listarContatos();

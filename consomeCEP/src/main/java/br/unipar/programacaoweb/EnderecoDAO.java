package br.unipar.programacaoweb;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import javax.swing.*;
import java.util.List;

public class EnderecoDAO {
    public static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("consomeCepPU");

    public void salvar(Endereco endereco) {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(endereco);
            em.getTransaction().commit();
            em.close();

        }catch(Exception e){
            em.getTransaction().rollback();
            e.printStackTrace();

        }finally{
            em.close();
        }
    }
    public Endereco buscarPorCep(String cep) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Endereco e WHERE e.cep = :cep", Endereco.class)
                    .setParameter("cep", cep)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } finally {
            em.close();
        }
    }

      /*  public List<Endereco> buscarTodos() {
            EntityManager m = emf.createEntityManager();
            try {
                return (Endereco) em.createQuery("from Endereco", Endereco.class).getResultList();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }finally {
                em.close();
            }*/

       // }
    }


